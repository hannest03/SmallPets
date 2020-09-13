package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
23.08.2020 11:02

*/

import it.smallcode.smallpets.core.manager.InventoryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    private InventoryManager inventoryManager;

    public InventoryCloseListener(InventoryManager inventoryManager){

        this.inventoryManager = inventoryManager;

    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){

        if(e.getView().getTitle().equals("§eSmallPets")){

            if(inventoryManager.getConvertingPets().contains(e.getPlayer().getUniqueId().toString())){

                inventoryManager.getConvertingPets().remove(e.getPlayer().getUniqueId().toString());

            }

        }

    }

}
