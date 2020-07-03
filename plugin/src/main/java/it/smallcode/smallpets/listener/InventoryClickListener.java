package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
03.07.2020 18:58

*/

import it.smallcode.smallpets.SmallPets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(e.getView().getTitle().equals("§eSmallPets")){

            e.setCancelled(true);

            if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                if (e.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) {

                    String[] nameSplit = e.getCurrentItem().getItemMeta().getDisplayName().split(" ");

                    String type = nameSplit[nameSplit.length - 1];

                    SmallPets.getInstance().getPetManager().spawnPet(type, (Player) e.getWhoClicked(), 0);

                    e.getWhoClicked().closeInventory();

                    e.getWhoClicked().sendMessage(SmallPets.getInstance().PREFIX + "Your pet was summoned");

                }

            }

        }

    }

}
