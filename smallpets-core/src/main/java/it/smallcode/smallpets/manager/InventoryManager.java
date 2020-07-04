package it.smallcode.smallpets.manager;
/*

Class created by SmallCode
03.07.2020 16:35

*/

import it.smallcode.smallpets.pets.Pet;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class InventoryManager {

    protected InventoryCache inventoryCache;

    public InventoryManager(InventoryCache inventoryCache){

        this.inventoryCache = inventoryCache;

    }

    public abstract void openPetsMenu(List<Pet> pets, Player p);

}
