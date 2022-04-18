package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
20.07.2021 12:11

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.logger.Logger;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.utils.FileUtils;
import it.smallcode.smallpets.core.utils.PetLoader;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class PetManager {

    private final Logger logger = SmallPetsCommons.getSmallPetsCommons().getLogger();

    private static final String[] configPets = new String[]{
            "fish",
            "monkey",
            "panda",
            "penguin",
            "silverfish",
            "skeleton",
            "tiger"
    };

    @Getter
    protected Map<NamespaceKey, Object> petMap = new HashMap<>();

    public void registerPetClasses(){}

    public void loadConfigPets(){
        File directory = new File(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin().getDataFolder() + "/pets");
        if(!directory.exists()){
            directory.mkdirs();
            for(String s : configPets){
                FileUtils.insertData("pets/" + s + ".json", directory.getAbsolutePath() + File.separator + s + ".json", SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());
            }
        }
        loadDirectory(directory);
    }

    private void loadDirectory(File directory){
        for(File file : directory.listFiles()){
            if(file.isDirectory()){
                loadDirectory(file);
                continue;
            }
           logger.println("Loading " + file.getName() + "...");
            Pet pet = PetLoader.loadPet(FileUtils.loadToJson(file));
            if(pet == null){
                logger.error("Couldn't load " + file.getName() + "!");
                continue;
            }

            logger.println("Loaded " + pet.getNamespace() + ":" + pet.getId() + " pet!");
            petMap.put(new NamespaceKey(pet.getNamespace(), pet.getId()), pet);
        }
    }

    public void registerCraftingRecipe(){
        petMap.keySet().iterator().forEachRemaining(namespaceKey -> {
            registerRecipe(namespaceKey.getNamespace(), namespaceKey.getId());
        });
    }

    private void registerRecipe(String namespace, String type){
        Pet pet = PetFactory.createPet(namespace, type, null, null, 0L);
        pet.registerRecipe();
    }

    public void removeCraftingRecipe(){
        petMap.keySet().iterator().forEachRemaining(namespaceKey -> {
            removeRecipe(namespaceKey.getNamespace(), namespaceKey.getId());
        });
    }

    private void removeRecipe(String namespace, String type){
        Pet pet = PetFactory.createPet(namespace, type, null, null, 0L);
        pet.removeRecipe();
    }

    public void registerPet(String namespace, String id, Class clazz){
        try {
            Constructor constructor = clazz.getConstructor();
            Pet pet = (Pet) constructor.newInstance();
            pet.setId(id);
            pet.setNamespace(namespace);
            pet.setTranslationKey("pet." + pet.getId());

            NamespaceKey namespaceKey = new NamespaceKey(namespace, id);
            petMap.put(namespaceKey, clazz);
            //registerRecipe(namespace, id);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    public Object getPet(String namespace, String id){
        if(namespace == null)
            return getPet(id);

        Object pet = petMap.get(new NamespaceKey(namespace, id));
        return pet;
    }

    public Object getPet(String id){
        Optional<NamespaceKey> optNamespaceKey = petMap.keySet()
                .stream()
                .filter(namespaceKey -> namespaceKey.getId().equals(id))
                .findFirst();
        if(!optNamespaceKey.isPresent())
            return null;

        NamespaceKey namespaceKey = optNamespaceKey.get();
        return petMap.get(namespaceKey);
    }

    public String getPetNamespace(String id){
        Optional<NamespaceKey> optNamespaceKey = petMap.keySet()
                .stream()
                .filter(namespaceKey -> namespaceKey.getId().equals(id))
                .findFirst();
        if(!optNamespaceKey.isPresent())
            return null;

        return optNamespaceKey.get().getNamespace();
    }

    public List<String> getPetKeys(){
        List<String> keys = petMap.keySet()
                                .stream()
                                .map(e -> e.getNamespace() + ":" + e.getId())
                                .collect(Collectors.toList());
        return keys;
    }

    @Data
    public static class NamespaceKey{
        private String namespace;
        private String id;
        public NamespaceKey(String namespace, String id){
            this.namespace = namespace;
            this.id = id;
        }
    }

}
