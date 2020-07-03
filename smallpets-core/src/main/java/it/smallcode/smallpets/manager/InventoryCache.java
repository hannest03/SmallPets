package it.smallcode.smallpets.manager;
/*

Class created by SmallCode
03.07.2020 16:30

*/

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InventoryCache {

    private HashMap<Player, Inventory> inventoryHashMap;

    public InventoryCache(){

        inventoryHashMap = new HashMap<>();

    }

    public Inventory getInventory(Player p){

        if(!inventoryHashMap.containsKey(p)){

            Inventory inv = Bukkit.createInventory(p, 9*5, "§eSmallPets");

            inventoryHashMap.put(p, inv);

        }

        return inventoryHashMap.get(p);

    }

    public void removeInventory(Player p){

        if(inventoryHashMap.containsKey(p))
            inventoryHashMap.remove(p);

    }

    public void removeAll(){

        for(int i = inventoryHashMap.size() - 1; i >= 0; i--){

            removeInventory((Player) inventoryHashMap.keySet().toArray()[i]);

        }

    }

}
