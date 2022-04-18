package it.smallcode.smallpets.core.convert;
/*

Class created by SmallCode
12.03.2022 12:28

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.logger.Logger;
import it.smallcode.smallpets.core.manager.types.Settings;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

public class ConversionManager {

    private static final Logger logger = SmallPetsCommons.getSmallPetsCommons().getLogger();

    public static void convertFromFile(Consumer<ConversionResult> consumer){
        final File dir = new File(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin().getDataFolder().getPath() + "/users");
        if(!dir.exists()) return;

        File[] files = dir.listFiles();
        if(files == null) return;

        for(File file : files){
            final String uuid = file.getName().replaceFirst("[.][^.]+$", "");

            final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            final Map<String, Object> data = cfg.getValues(true);
            User user = unserialize(data);
            user.setUuid(uuid);

            try {
                SmallPetsCommons.getSmallPetsCommons().getUserManager().saveUserExceptions(user);
                consumer.accept(new ConversionResult(ConversionResult.ConversionResultEvent.LOADED, user));
            }catch(Exception ex){
                consumer.accept(new ConversionResult(ConversionResult.ConversionResultEvent.FAILED, user));
            }

        }
    }

    private static User unserialize(Map<String, Object> data){
        User user = new User();

        Settings settings = new Settings();
        if(data.get("settings") != null){
            settings.unserialize(memorySectionToMap((MemorySection) data.get("settings")));
        }

        List<Pet> pets = new LinkedList<>();
        List<Map<String, Object>> petDatas = (List<Map<String, Object>>) data.get("pets");
        for(Map<String, Object> petData : petDatas){
            Pet pet = unserializePet(petData);
            if(pet != null)
                pets.add(pet);
        }

        user.setSettings(settings);
        user.setPets(pets);

        if(!(data.get("selected")).equals("null")) {
            try {
                UUID petUUID = UUID.fromString((String) data.get("selected"));
                user.setSelectedSafe(user.getPetFromUUID(petUUID));
            } catch (IllegalArgumentException ex) {
                logger.warn("Invalid pet uuid found! " + data.get("selected"));
                logger.warn("This error can be ignored");
            }
        }

        return user;
    }

    private static Map<String, Object> memorySectionToMap(MemorySection memorySection){
        Map<String, Object> map = new HashMap<>();
        for(String key : memorySection.getKeys(true))
            map.put(key, memorySection.get(key));
        return map;
    }

    /**
     *
     * Unserializes the data of a pet
     *
     * @param data - the data
     * @return - the unserialized pet
     */
    private static Pet unserializePet(Map<String, Object> data){
        String id = (String) data.get("id");
        if(id == null){
            id = (String) data.get("type");
        }

        String namespace = (String) data.get("namespace");

        UUID petUUID;
        if(data.get("uuid") != null)
            petUUID = UUID.fromString((String) data.get("uuid"));
        else
            petUUID = UUID.randomUUID();

        long exp = Long.valueOf((String) data.get("exp"));

        Object pet;
        if(namespace == null){
            pet = SmallPetsCommons.getSmallPetsCommons().getPetManager().getPet(id);
        }else{
            pet = SmallPetsCommons.getSmallPetsCommons().getPetManager().getPet(namespace, id);
        }

        String name = null;
        if(data.get("name") != null){
            name = (String) data.get("name");
        }

        if(pet != null){
            Pet ret;
            if(namespace == null) {
                ret = PetFactory.createPet(id, petUUID, null, exp);
            }else{
                ret = PetFactory.createPet(namespace, id, petUUID, null, exp);
            }
            if(name != null && ret != null){
                ret.setName(name);
            }

            return ret;
        }

        return null;
    }

    public static class ConversionResult{
        public ConversionResultEvent event;
        public User user;

        public ConversionResult(ConversionResultEvent event, User user) {
            this.event = event;
            this.user = user;
        }

        public enum ConversionResultEvent{
            LOADED,
            FAILED
        }
    }

}
