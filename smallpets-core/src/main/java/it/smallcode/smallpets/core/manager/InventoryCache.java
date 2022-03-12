package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
03.07.2020 16:30

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

/**
 *
 * The inventory cache keeps track of the inventories of a player
 *
 */
public class InventoryCache {

    private final LanguageManager LANGUAGE_MANAGER;
    private HashMap<Player, Inventory> inventoryHashMap;

    /**
     *
     * Creates a inventory cache object
     *
     */

    public InventoryCache(LanguageManager languageManager){
        this.LANGUAGE_MANAGER = languageManager;

        inventoryHashMap = new HashMap<>();

    }

    /**
     *
     * Gets the inventory of a player,
     * when the inventory doesn't exist already it will be created
     *
     * @param p - the player
     * @return the inventory
     */

    public Inventory getInventory(Player p){

        if(!inventoryHashMap.containsKey(p)){

            Inventory inv = Bukkit.createInventory(p, 9*5, LANGUAGE_MANAGER.getLanguage().getStringFormatted("inventory.name"));

            inventoryHashMap.put(p, inv);

        }

        return inventoryHashMap.get(p);

    }

    /**
     *
     * Removes the inventory of a player
     *
     * @param p - the player
     */

    public void removeInventory(Player p){

        if(inventoryHashMap.containsKey(p))
            inventoryHashMap.remove(p);

    }

    /**
     *
     * Removes all the inventories created by this object
     *
     */

    public void removeAll(){

        for(int i = inventoryHashMap.size() - 1; i >= 0; i--){

            removeInventory((Player) inventoryHashMap.keySet().toArray()[i]);

        }

    }

}
