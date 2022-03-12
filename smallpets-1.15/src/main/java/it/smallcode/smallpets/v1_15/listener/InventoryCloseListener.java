package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
23.08.2020 11:02

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.InventoryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e){

        if(e.getView().getTitle().equals(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.name"))){

            InventoryManager inventoryManager = SmallPetsCommons.getSmallPetsCommons().getInventoryManager();

            if(inventoryManager.getConvertingPets().contains(e.getPlayer().getUniqueId().toString())){

                inventoryManager.getConvertingPets().remove(e.getPlayer().getUniqueId().toString());

            }

        }

    }

}
