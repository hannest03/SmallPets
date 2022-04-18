package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
03.07.2020 16:35

*/

import org.bukkit.entity.Player;

import java.util.ArrayList;

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
     * @param xpMultiplier - the current xp multiplier
     */

    public InventoryManager(double xpMultiplier){

        this.xpMultiplier = xpMultiplier;

        this.convertingPets = new ArrayList<>();

    }

    /**
     *
     * Opens the pet menu
     *
     * @param page - the page which is currently being viewed
     * @param p - the player
     */

    public abstract void openPetsMenu(int page, Player p);

    public abstract void openRecipeBook(int page, Player p);
    public abstract void openRecipe(String petID, Player p, int index);

    public void setXpMultiplier(double xpMultiplier) {
        this.xpMultiplier = xpMultiplier;
    }

    public ArrayList<String> getConvertingPets() {
        return convertingPets;
    }
}
