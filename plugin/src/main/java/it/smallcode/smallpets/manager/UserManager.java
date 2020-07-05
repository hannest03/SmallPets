package it.smallcode.smallpets.manager;
/*

Class created by SmallCode
03.07.2020 22:00

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.manager.types.User;
import it.smallcode.smallpets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserManager {

    private ArrayList<User> users;

    public UserManager(){

        users = new ArrayList<>();

    }

    public void loadUser(String uuid, PetMapManager petMapManager){

        if(!alreadyLoaded(uuid)) {

            if (!new File(SmallPets.getInstance().getDataFolder().getPath() + "/users").exists())
                new File(SmallPets.getInstance().getDataFolder().getPath() + "/users").mkdirs();

            File userFile = new File(SmallPets.getInstance().getDataFolder().getPath() + "/users", uuid + ".yml");

            if (!userFile.exists()) {

                users.add(new User(uuid));

            } else {

                FileConfiguration cfg = YamlConfiguration.loadConfiguration(userFile);

                Map<String, Object> data = cfg.getValues(true);

                users.add(new User(userFile.getName().replaceFirst("[.][^.]+$", ""), data, SmallPets.getInstance().getPetMapManager()));

            }

        }

    }

    private boolean alreadyLoaded(String uuid){

        Optional<User> result = users.stream().filter(user -> user.getUuid().equals(uuid)).findFirst();

        if(!result.isPresent())
            return false;
        else
            return true;

    }

    public void saveUsers(){

        if(!new File(SmallPets.getInstance().getDataFolder().getPath() + "/users").exists())
            new File(SmallPets.getInstance().getDataFolder().getPath() + "/users").mkdirs();

        for(User user : users){

            File userFile = new File(SmallPets.getInstance().getDataFolder().getPath() + "/users", user.getUuid() + ".yml");

            if(!userFile.exists()) {

                try {

                    userFile.createNewFile();

                } catch (IOException ex) {

                    ex.printStackTrace();

                }
            }

            FileConfiguration cfg = YamlConfiguration.loadConfiguration(userFile);

            Map<String, Object> data = user.serialize();

            cfg.set("selected", data.get("selected"));
            cfg.set("pets", data.get("pets"));

            try {

                cfg.save(userFile);

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

    public void giveUserPet(String type, String uuid){

        User user = getUser(uuid);

        if(user != null){

            if(SmallPets.getInstance().getPetMapManager().getPetMap().containsKey(type)){

                if(user.getPetFromType(type) == null) {

                    try {

                        Constructor constructor = SmallPets.getInstance().getPetMapManager().getPetMap().get(type).getConstructor(Player.class, Long.class);

                        Pet pet = (Pet) constructor.newInstance(Bukkit.getPlayer(UUID.fromString(uuid)), 0L);

                        user.getPets().add(pet);

                    } catch (NoSuchMethodException ex) {

                        ex.printStackTrace();

                    } catch (IllegalAccessException ex) {

                        ex.printStackTrace();

                    } catch (InstantiationException ex) {

                        ex.printStackTrace();

                    } catch (InvocationTargetException ex) {

                        ex.printStackTrace();

                    }

                }

            }

        }

    }

    public void despawnPets(){

        for(User user : users){

            user.despawnSelected();

        }

    }

    public User getUser(String uuid){

        Optional<User> result = users.stream().filter(user -> user.getUuid().equals(uuid)).findFirst();

        if(result.isPresent())
            return result.get();

        return null;

    }

}
