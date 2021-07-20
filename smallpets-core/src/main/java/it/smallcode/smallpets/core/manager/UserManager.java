package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
03.07.2020 22:00

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * The user manager is being used to load, save and keep track of all the users
 *
 */
public class UserManager {

    private ArrayList<User> users;

    private boolean useProtocolLib;

    /**
     *
     * Creates a user manager object
     *
     */
    public UserManager(boolean useProtocolLib){

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
    public void loadUser(String uuid){

        if(!alreadyLoaded(uuid)) {

            if (!new File(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin().getDataFolder().getPath() + "/users").exists())
                new File(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin().getDataFolder().getPath() + "/users").mkdirs();

            File userFile = new File(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin().getDataFolder().getPath() + "/users", uuid + ".yml");

            if (!userFile.exists()) {

                users.add(new User(uuid));

            } else {

                FileConfiguration cfg = YamlConfiguration.loadConfiguration(userFile);

                Map<String, Object> data = cfg.getValues(true);

                users.add(new User(userFile.getName().replaceFirst("[.][^.]+$", ""), data));

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

        if(!new File(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin().getDataFolder().getPath() + "/users").exists())
            new File(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin().getDataFolder().getPath() + "/users").mkdirs();

        for(User user : users){

            File userFile = new File(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin().getDataFolder().getPath() + "/users", user.getUuid() + ".yml");

            if(!userFile.exists()) {

                try {

                    userFile.createNewFile();

                } catch (IOException ex) {

                    ex.printStackTrace();

                }
            }

            FileConfiguration cfg = YamlConfiguration.loadConfiguration(userFile);

            Map<String, Object> data = user.serialize();

            for(String key : data.keySet()){

                cfg.set(key, data.get(key));

            }

            try {

                cfg.save(userFile);

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

    /**
     *
     * Gives a player all pets
     *
     * @param uuid - the uuid of the player
     * @param exp - the amount of exp the pets have
     * @return the boolean is true if the pet was successfully added to the player
     */
    public boolean giveUserAllPets(String uuid, long exp){
        for(PetManager.NamespaceKey namespaceKey : SmallPetsCommons.getSmallPetsCommons().getPetManager().getPetMap().keySet()){
            giveUserPet(namespaceKey.getNamespace(), namespaceKey.getId(), uuid, exp);
        }
        return true;
    }

    /**
     *
     * Gives a player all pets
     *
     * @param uuid - the uuid of the player
     * @return the boolean is true if the pet was successfully added to the player
     */
    public boolean giveUserAllPets(String uuid){
        return giveUserAllPets(uuid, 0);
    }

    /**
     *
     * Gives a player a pet
     *
     * @param type - the type of the pet
     * @param uuid - the uuid of the player
     * @return the boolean is true if the pet was successfully added to the player,<br> if the player already had the pet or there was an error false will be returned
     */
    public boolean giveUserPet(String namespace, String type, String uuid){

        return giveUserPet(namespace, type, uuid, 0L);

    }

    /**
     *
     * Gives a player a pet
     *
     * @param type - the type of the pet
     * @param uuid - the uuid of the player
     * @param exp - the amount of exp the pet has
     * @return the boolean is true if the pet was successfully added to the player,<br> if the player already had the pet or there was an error false will be returned
     */
    public boolean giveUserPet(String namespace, String type, String uuid, Long exp){

        User user = getUser(uuid);

        if(user != null){

            Player p = Bukkit.getPlayer(UUID.fromString(uuid));

            if(p == null || !p.isOnline())
                return false;

            if(SmallPetsCommons.getSmallPetsCommons().isRequirePermission() && !p.hasPermission("smallpets.allow." + type)) {

                p.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("noPerms"));

                return false;

            }

            if(!SmallPetsCommons.getSmallPetsCommons().isRequirePermission() && (p.hasPermission("smallpets.forbid." + type) && !p.isOp())) {

                p.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("noPerms"));

                return false;

            }

            Pet pet = PetFactory.createNewPet(namespace, type, Bukkit.getPlayer(UUID.fromString(uuid)), exp);
            user.getPets().add(pet);

            return true;

        }

        return false;

    }

    /**
     *
     * Gives the player a unlock item
     *
     * @param type - the type of the pet
     * @param p - the player
     * @param exp - the amount of exp the pet has
     * @return the boolean is true if the unlock item was successfully given to the player,<br> if there was an error false will be returned
     */
    public boolean giveUserUnlockPetItem(String namespace, String type, Player p, Long exp){

        Pet pet = PetFactory.createNewPet(namespace, type, p, exp);
        if(pet != null)
            p.getInventory().addItem(pet.getUnlockItem());

        return false;

    }

    /**
     *
     * Removes all pets from player
     *
     * @param uuid - the uuid of the player
     * @return the boolean is true if the pet was successfully removed from to the player,<br> if the player doesn't exist false gets returned
     */
    public boolean removeUserAllPets(String uuid){
        User user = getUser(uuid);
        if(user == null)
            return false;
        List<PetManager.NamespaceKey> types = user.getPets().stream()
                .map(pet -> new PetManager.NamespaceKey(pet.getNamespace(), pet.getId()))
                .collect(Collectors.toList());
        for(PetManager.NamespaceKey key : types){
            if(user.getPetFromNamespaceAndType(key.getNamespace(), key.getId()) != null) {
                if(user.getSelected() != null) {
                    if (user.getSelected().getId().equals(key.getId()) && user.getSelected().getNamespace().equals(key.getNamespace())) {
                        user.despawnSelected();
                    }
                }
                Pet pet = user.getPetFromNamespaceAndType(key.getNamespace(), key.getId());
                user.getPets().remove(pet);
            }
        }
        return true;
    }

    /**
     *
     * Removes a player a pet
     *
     * @param type - the type of the pet
     * @param uuid - the uuid of the player
     * @return the boolean is true if the pet was successfully removed from to the player,<br> if the player hasn't got the pet or there was an error false will be returned
     */
    public boolean removeUserPet(String namespace, String type, String uuid){

        User user = getUser(uuid);

        if(user != null){

            if(user.getPetFromNamespaceAndType(namespace, type) != null) {

                if(user.getSelected() != null) {

                    if (user.getSelected().getId().equals(type)) {

                        user.despawnSelected();

                    }

                }

                Pet pet = user.getPetFromNamespaceAndType(namespace, type);

                user.getPets().remove(pet);

                return true;

            }

        }

        return false;

    }

    /**
     *
     * Removes a player a pet
     *
     * @param petUUID - the uuid of the pet
     * @param uuid - the uuid of the player
     * @return the boolean is true if the pet was successfully removed from to the player,<br> if the player hasn't got the pet or there was an error false will be returned
     */
    public boolean removeUserPet(UUID petUUID, String uuid){

        User user = getUser(uuid);

        if(user != null){

            if(user.getPetFromUUID(petUUID) != null) {

                if(user.getSelected() != null) {

                    if (user.getSelected().getUuid().equals(petUUID)) {

                        user.despawnSelected();

                    }

                }

                Pet pet = user.getPetFromUUID(petUUID);

                user.getPets().remove(pet);

                return true;

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
     * Update pets for player
     *
     */
    public void updatePets(Player player){

        for(User user : users){

            user.despawnSelected(player);
            user.spawnSelected(player);

        }

    }

    /**
     *
     * Spawns all the pets from the loaded users
     *
     */

    public void spawnPets(){

        for(User user : users){

            if(Bukkit.getOfflinePlayer(user.getUuid()).isOnline())
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

    public ArrayList<User> getUsers() {
        return users;
    }

}
