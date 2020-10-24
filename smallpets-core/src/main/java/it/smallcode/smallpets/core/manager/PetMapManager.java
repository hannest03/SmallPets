package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
02.07.2020 15:11

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

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

            try {

                Constructor constructor = petMap.get(id).getConstructor(String.class, Player.class, Long.class, Boolean.class);

                Pet pet = (Pet) constructor.newInstance(id, null, 0L, false);

                pet.registerRecipe(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

            } catch (NoSuchMethodException ex) {

                ex.printStackTrace();

            } catch (IllegalAccessException ex) {

                ex.printStackTrace();

            } catch (InstantiationException ex) {

                ex.printStackTrace();

            } catch (InvocationTargetException ex) {

                ex.printStackTrace();

            }

        });

    }

    /**
     *
     * Registers a pet
     *
     */
    public void registerPet(String type, Class clazz){

        petMap.put(type, clazz);

        try {

            Constructor constructor = clazz.getConstructor(String.class, Player.class, Long.class, Boolean.class);

            Pet pet = (Pet) constructor.newInstance(type, null, 0L, false);

            pet.registerRecipe(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

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
