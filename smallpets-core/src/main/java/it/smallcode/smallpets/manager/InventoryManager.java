package it.smallcode.smallpets.manager;
/*

Class created by SmallCode
03.07.2020 16:35

*/

import it.smallcode.smallpets.languages.Language;
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.pets.Pet;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * The inventory manager opens the inventories of the plugin
 *
 */
public abstract class InventoryManager {

    protected JavaPlugin plugin;

    protected InventoryCache inventoryCache;
    protected ArrayList<String> convertingPets;

    protected LanguageManager languageManager;

    protected double xpMultiplier;

    /**
     *
     * Creates a inventory manager object
     *
     * @param inventoryCache - the inventoryCache
     */

    public InventoryManager(InventoryCache inventoryCache, LanguageManager languageManager, double xpMultiplier, JavaPlugin plugin){

        this.plugin = plugin;

        this.inventoryCache = inventoryCache;
        this.languageManager = languageManager;

        this.xpMultiplier = xpMultiplier;

        this.convertingPets = new ArrayList<>();

    }

    /**
     *
     * Opens the pet menu
     *
     * @param pets - a list of all the pets of a player
     * @param p - the player
     */

    public abstract void openPetsMenu(List<Pet> pets, Player p);

    public void setXpMultiplier(double xpMultiplier) {
        this.xpMultiplier = xpMultiplier;
    }

    public ArrayList<String> getConvertingPets() {
        return convertingPets;
    }
}
