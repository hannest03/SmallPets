package it.smallcode.smallpets.api;
/*

Class created by SmallCode
18.05.2021 15:48

*/

import it.smallcode.smallpets.api.exceptions.NoSuchPetTypeException;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public class SmallPetsAPI {

    /**
     *
     * Returns a set with all registered pet ids
     *
     * @return a list with all pet ids
     */
    public static Set<String> getAllPetIDs(){
       return SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().keySet();
    }

    /**
     *
     * Returns the pet of a player.
     *
     * If the user couldn't be found null gets return.
     * If the pet couldn't be found or the player hasn't unlocked it null gets returned.
     *
     * @param p - the player
     * @param type - the pet type
     * @return the pet
     */
    public static Pet getPet(Player p, String type){
        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());
        if(user == null)
            return null;
        return user.getPetFromType(type);
    }

    /**
     *
     * Returns the pet of a player.
     *
     * If the user couldn't be found null gets return.
     * If the pet couldn't be found or the player hasn't unlocked it null gets returned.
     *
     * @param p - the player
     * @param uuid - the uuid of the pet
     * @return the pet
     */
    public static Pet getPet(Player p, UUID uuid){
        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());
        if(user == null)
            return null;
        return user.getPetFromUUID(uuid);
    }

    /**
     *
     * Returns an item with which the pet can be unlocked
     *
     * @param type - the pet type
     * @return An item to unlock the pet
     * @throws NoSuchPetTypeException if pet type isn't registered
     */
    public static ItemStack createUnlockItem(String type) throws NoSuchPetTypeException {
        return createUnlockItem(type, 0L);
    }

    /**
     *
     * Returns an item with which the pet can be unlocked
     *
     * @param type - the pet type
     * @param exp - the exp of the unlocked pet
     * @return An item to unlock the pet
     * @throws NoSuchPetTypeException if pet type isn't registered
     * @throws IllegalArgumentException if the exp is lesser than zero
     */
    public static ItemStack createUnlockItem(String type, long exp) throws NoSuchPetTypeException, IllegalArgumentException {
        if(exp < 0)
            throw new IllegalArgumentException("The experience has to be bigger or equal to zero!");
        Pet pet = PetFactory.createNewPet(type, null, exp, SmallPetsCommons.getSmallPetsCommons().isUseProtocollib());
        return pet.getUnlockItem();
    }

    /**
     *
     * Returns the user.
     *
     * Returns null if the user isn't registered
     *
     * @param uuid - the uuid of the user
     * @return The user object
     */
    public static User getUser(UUID uuid){
        return SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(uuid.toString());
    }

    /**
     *
     * Registers an ability
     *
     * Returns false if an ability with this ID has already been registered
     *
     * @param abilityID - the ID of the ability
     * @param clazz - the class of the ability
     * @return if the operation was successful
     */

    public static boolean createAbility(String abilityID, Class clazz){
        if(SmallPetsCommons.getSmallPetsCommons().getAbilityManager().hasID(abilityID)){
            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§bAPI§8: §7Ability with this id already registered!");
            return false;
        }
        SmallPetsCommons.getSmallPetsCommons().getAbilityManager().registerAbility(abilityID, clazz);
        return true;

    }

}
