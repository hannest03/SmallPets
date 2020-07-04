package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
04.07.2020 16:32

*/

import it.smallcode.smallpets.SmallPets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class ArmorStandInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerArmorStandManipulateEvent e){

        if(e.getRightClicked().getCustomName() != null){

            for(String types : SmallPets.getInstance().getPetMapManager().getPetMap().keySet()){

                if(e.getRightClicked().getCustomName().endsWith(types)){

                    e.setCancelled(true);

                    break;

                }

            }

        }

    }

}
