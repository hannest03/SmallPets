package it.smallcode.smallpets.manager;
/*

Class created by SmallCode
02.07.2020 15:11

*/

import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.pets.Pet;
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

    public void registerCraftingRecipe(Plugin plugin, LanguageManager languageManager){

        petMap.values().iterator().forEachRemaining(aClass -> {

            try {

                Constructor constructor = aClass.getConstructor(Player.class, Long.class, Boolean.class, LanguageManager.class);

                Pet pet = (Pet) constructor.newInstance(null, 0L, false, languageManager);

                pet.registerRecipe(plugin);

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
    public void registerPet(String type, Class clazz, Plugin plugin, LanguageManager languageManager){

        petMap.put(type, clazz);

        try {

            Constructor constructor = clazz.getConstructor(Player.class, Long.class, Boolean.class, LanguageManager.class);

            Pet pet = (Pet) constructor.newInstance(null, 0L, false, languageManager);

            pet.registerRecipe(plugin);

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
