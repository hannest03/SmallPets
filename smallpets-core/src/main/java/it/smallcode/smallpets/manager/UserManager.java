package it.smallcode.smallpets.manager;
/*

Class created by SmallCode
03.07.2020 22:00

*/

import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.types.User;
import it.smallcode.smallpets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * The user manager is being used to load, save and keep track of all the users
 *
 */
public class UserManager {

    private JavaPlugin plugin;

    private LanguageManager languageManager;
    private PetMapManager petMapManager;

    private ArrayList<User> users;

    private boolean useProtocolLib;

    /**
     *
     * Creates a user manager object
     *
     */
    public UserManager(JavaPlugin plugin, LanguageManager languageManager, PetMapManager petMapManager, boolean useProtocolLib){

        this.plugin = plugin;

        this.languageManager = languageManager;
        this.petMapManager = petMapManager;

        this.useProtocolLib = useProtocolLib;

        users = new ArrayList<>();

    }

    /**
     *
     * Loads a user from his file,<br>
     * if the file doesn't exist a new user will be created
     *
     * @param uuid - the uuid of the player which should be loaded
     * @param petMapManager - the petMapManager to check if the pet is registered
     */
    public void loadUser(String uuid, PetMapManager petMapManager){

        if(!alreadyLoaded(uuid)) {

            if (!new File(plugin.getDataFolder().getPath() + "/users").exists())
                new File(plugin.getDataFolder().getPath() + "/users").mkdirs();

            File userFile = new File(plugin.getDataFolder().getPath() + "/users", uuid + ".yml");

            if (!userFile.exists()) {

                users.add(new User(uuid, plugin));

            } else {

                FileConfiguration cfg = YamlConfiguration.loadConfiguration(userFile);

                Map<String, Object> data = cfg.getValues(true);

                users.add(new User(userFile.getName().replaceFirst("[.][^.]+$", ""), data, petMapManager, plugin, useProtocolLib, languageManager));

            }

        }

    }

    /**
     *
     * Checks if the user is already loaded
     *
     * @param uuid - the uuid of the user
     * @return returns true if the player already was loaded
     */
    private boolean alreadyLoaded(String uuid){

        Optional<User> result = users.stream().filter(user -> user.getUuid().equals(uuid)).findFirst();

        if(!result.isPresent())
            return false;
        else
            return true;

    }

    /**
     *
     * Saves all loaded users to their file
     *
     */
    public void saveUsers(){

        if(!new File(plugin.getDataFolder().getPath() + "/users").exists())
            new File(plugin.getDataFolder().getPath() + "/users").mkdirs();

        for(User user : users){

            File userFile = new File(plugin.getDataFolder().getPath() + "/users", user.getUuid() + ".yml");

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

    /**
     *
     * Gives a player a pet
     *
     * @param type - the type of the pet
     * @param uuid - the uuid of the player
     * @return the boolean is true if the pet was successfully added to the player,<br> if the player already had the pet or there was an error false will be returned
     */
    public boolean giveUserPet(String type, String uuid){

        User user = getUser(uuid);

        if(user != null){

            if(petMapManager.getPetMap().containsKey(type)){

                if(user.getPetFromType(type) == null) {

                    try {

                        Constructor constructor = petMapManager.getPetMap().get(type).getConstructor(Player.class, Long.class, Boolean.class, LanguageManager.class);

                        Pet pet = (Pet) constructor.newInstance(Bukkit.getPlayer(UUID.fromString(uuid)), 0L, useProtocolLib, languageManager);

                        user.getPets().add(pet);

                        return true;

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

        return false;

    }

    /**
     *
     * Removes a player a pet
     *
     * @param type - the type of the pet
     * @param uuid - the uuid of the player
     * @return the boolean is true if the pet was successfully removed from to the player,<br> if the player hasn't got the pet or there was an error false will be returned
     */
    public boolean removeUserPet(String type, String uuid){

        User user = getUser(uuid);

        if(user != null){

            if(petMapManager.getPetMap().containsKey(type)){

                if(user.getPetFromType(type) != null) {

                    if(user.getSelected().getName().equals(type)){

                        user.despawnSelected();

                    }

                    Pet pet = user.getPetFromType(type);

                    user.getPets().remove(pet);

                    return true;

                }

            }

        }

        return false;

    }

    /**
     *
     * Despawnes all the pets from the loaded users
     *
     */

    public void despawnPets(){

        for(User user : users){

            user.despawnSelected();

        }

    }

    /**
     *
     * Spawns all the pets from the loaded users
     *
     */

    public void spawnPets(){

        for(User user : users){

            user.spawnSelected();

        }

    }

    /**
     *
     * Returns the user
     *
     * @param uuid - the uuid of the player
     * @return returns the player, if the player wasn't loaded or couldn't be found it returns null
     */

    public User getUser(String uuid){

        Optional<User> result = users.stream().filter(user -> user.getUuid().equals(uuid)).findFirst();

        if(result.isPresent())
            return result.get();

        return null;

    }

}
