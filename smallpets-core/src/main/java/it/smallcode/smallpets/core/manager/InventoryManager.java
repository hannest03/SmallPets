package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
03.07.2020 16:35

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.pets.Pet;
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

    protected ArrayList<String> convertingPets;

    protected double xpMultiplier;

    /**
     *
     * Creates a inventory manager object
     *
     * @param inventoryCache - the inventoryCache
     */

    public InventoryManager(double xpMultiplier){

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

    public abstract void openPetsMenu(int page, Player p);

    public void setXpMultiplier(double xpMultiplier) {
        this.xpMultiplier = xpMultiplier;
    }

    public ArrayList<String> getConvertingPets() {
        return convertingPets;
    }
}
