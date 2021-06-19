package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
02.07.2020 15:11

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * The petMapManager keeps track of all the pets available in each version
 *
 */

public abstract class PetMapManager {

    protected HashMap<String, Class> petMap;

    /**
     *
     * Creates an PetMapManager
     *
     */

    public PetMapManager(){

        petMap = new HashMap<String, Class>();

    }

    /**
     *
     * Returns the petMap
     *
     * @return the petMap
     */

    public HashMap<String, Class> getPetMap() {
        return petMap;
    }

    /**
     *
     * Registers all the pets of a version
     *
     */

    public abstract void registerPets();

    /**
     *
     * Registers all crafting recipes
     *
     */

    public void registerCraftingRecipe(){

        petMap.keySet().iterator().forEachRemaining(id -> {

            registerRecipe(id);

        });

    }

    /**
     *
     * Registers a pet
     *
     */
    public void registerPet(String type, Class clazz){

        petMap.put(type, clazz);

        registerRecipe(type);
    }

    private void registerRecipe(String type){
        Pet pet = PetFactory.createPet(type, null, null, 0L, false);
        pet.registerRecipe(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());
    }

}
