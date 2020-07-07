package it.smallcode.smallpets.manager;
/*

Class created by SmallCode
02.07.2020 15:11

*/

import org.bukkit.plugin.Plugin;

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

    public abstract void registerCraftingRecipe(Plugin plugin);

}
