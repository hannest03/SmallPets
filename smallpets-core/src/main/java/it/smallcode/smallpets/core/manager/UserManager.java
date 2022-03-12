package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
03.07.2020 22:00

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.database.Database;
import it.smallcode.smallpets.core.database.dao.PetDAO;
import it.smallcode.smallpets.core.database.dao.SettingsDAO;
import it.smallcode.smallpets.core.database.dao.UserDAO;
import it.smallcode.smallpets.core.database.dto.PetDTO;
import it.smallcode.smallpets.core.database.dto.SettingsDTO;
import it.smallcode.smallpets.core.database.dto.UserDTO;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.utils.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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

    private UserDAO userDAO;
    private SettingsDAO settingsDAO;
    private PetDAO petDAO;

    /**
     *
     * Creates a user manager object
     *
     */
    public UserManager(Database database){
        this.userDAO = database.getDao(UserDAO.class);
        this.settingsDAO = database.getDao(SettingsDAO.class);
        this.petDAO = database.getDao(PetDAO.class);

        this.useProtocolLib = SmallPetsCommons.getSmallPetsCommons().isUseProtocollib();

        users = new ArrayList<>();
    }

    /**
     *
     * Loads a user from his file,<br>
     * if the file doesn't exist a new user will be created
     *
     * @param uuid - the uuid of the player which should be loaded
     */
    public void loadUser(String uuid){
        if(!alreadyLoaded(uuid)) {
            // TODO: Convert old file to database
            try {
                UserDTO userDTO = userDAO.getUser(uuid);
                SettingsDTO[] settingsDTOs = settingsDAO.getSettings(uuid);
                PetDTO[] petDTOs = petDAO.getPets(uuid);

                if(userDTO == null) {
                    users.add(new User(uuid, SmallPetsCommons.getSmallPetsCommons().getJavaPlugin()));

                    UserDTO newUser = new UserDTO();
                    newUser.setUid(uuid);
                    userDAO.insertUser(newUser);

                    return;
                }

                User user = UserUtils.dtoToUser(userDTO, settingsDTOs, petDTOs);
                users.add(user);
            } catch (SQLException ex) {
                ex.printStackTrace();
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
        for(User user : users){
            saveUser(user);
        }
    }

    public void saveUser(User user){
        try{
            saveUserExceptions(user);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void saveUserExceptions(User user) throws SQLException {
        UserDTO userDTO = UserUtils.userToDTO(user);
        if(userDAO.existsUser(user.getUuid())) {
            userDAO.updateUser(userDTO);
        }else{
            userDAO.insertUser(userDTO);
        }

        SettingsDTO[] settingsDTOs = UserUtils.settingsToDTO(user.getUuid(), user.getSettings());
        for(SettingsDTO settingsDTO : settingsDTOs){
            if(this.settingsDAO.hasSetting(settingsDTO.getUid(), settingsDTO.getSname())){
                this.settingsDAO.updateSetting(settingsDTO);
            }else{
                this.settingsDAO.insertSetting(settingsDTO);
            }
        }

        PetDTO[] petDTOs = UserUtils.petsToDTO(user.getPets(), user.getUuid());
        for(PetDTO petDTO : petDTOs){
            if(this.petDAO.pidExists(petDTO.getPid())) {
                this.petDAO.updatePet(petDTO);
            }else{
                this.petDAO.insertPet(petDTO);
            }
        }
    }

    public void removeUserPet(User user, Pet pet){
        PetDTO petDTO = UserUtils.petToDTO(pet);
        try {
            this.petDAO.deletePet(petDTO);
            user.getPets().remove(pet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void saveUserAndRemoveFromCache(User user){
        saveUser(user);
        this.users.remove(user);
    }

    public void updatePet(Pet pet){
        try {
            this.petDAO.updatePet(UserUtils.petToDTO(pet));
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        for(String type : SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().keySet()){
            giveUserPet(type, uuid, exp);
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
    public boolean giveUserPet(String type, String uuid){

        return giveUserPet(type, uuid, 0L);

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
    public boolean giveUserPet(String type, String uuid, Long exp){

        User user = getUser(uuid);

        if(user != null){

            if(SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().containsKey(type)){

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

                Pet pet = PetFactory.createNewPet(type, Bukkit.getPlayer(UUID.fromString(uuid)), exp, useProtocolLib);
                user.getPets().add(pet);

                PetDTO petDTO = UserUtils.petToDTO(pet);
                try {
                    this.petDAO.insertPet(petDTO);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                return true;

            }

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
    public boolean giveUserUnlockPetItem(String type, Player p, Long exp){

        if(SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().containsKey(type)){

            Pet pet = PetFactory.createNewPet(type, p, exp, useProtocolLib);
            p.getInventory().addItem(pet.getUnlockItem());

        }else
            throw new IllegalArgumentException("Pet id isn't registered");

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
        List<String> types = user.getPets().stream()
                .map(pet -> pet.getID())
                .collect(Collectors.toList());
        for(String type : types){
            if(user.getPetFromType(type) != null) {
                if(user.getSelected() != null) {
                    if (user.getSelected().getID().equals(type)) {
                        user.despawnSelected();
                    }
                }
                Pet pet = user.getPetFromType(type);
                removeUserPet(user, pet);
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
    public boolean removeUserPet(String type, String uuid){

        User user = getUser(uuid);

        if(user != null){

            if(SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().containsKey(type)){

                if(user.getPetFromType(type) != null) {

                    if(user.getSelected() != null) {

                        if (user.getSelected().getID().equals(type)) {

                            user.despawnSelected();

                        }

                    }

                    Pet pet = user.getPetFromType(type);
                    removeUserPet(user, pet);

                    return true;

                }

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
                removeUserPet(user, pet);

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
        synchronized (users) {
            for (User user : users) {
                user.despawnSelected();
            }
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
        synchronized (users) {
            for (User user : users) {
                if (Bukkit.getOfflinePlayer(UUID.fromString(user.getUuid())).isOnline())
                    user.spawnSelected();
            }
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
