package it.smallcode.smallpets.manager;
/*

Class created by SmallCode
03.07.2020 16:35

*/

import it.smallcode.smallpets.languages.Language;
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.pets.Pet;
import org.bukkit.entity.Player;

import java.util.List;

/**
 *
 * The inventory manager opens the inventories of the plugin
 *
 */
public abstract class InventoryManager {

    protected InventoryCache inventoryCache;

    protected LanguageManager languageManager;

    /**
     *
     * Creates a inventory manager object
     *
     * @param inventoryCache - the inventoryCache
     */

    public InventoryManager(InventoryCache inventoryCache, LanguageManager languageManager){

        this.inventoryCache = inventoryCache;
        this.languageManager = languageManager;

    }

    /**
     *
     * Opens the pet menu
     *
     * @param pets - a list of all the pets of a player
     * @param p - the player
     */

    public abstract void openPetsMenu(List<Pet> pets, Player p);

}
