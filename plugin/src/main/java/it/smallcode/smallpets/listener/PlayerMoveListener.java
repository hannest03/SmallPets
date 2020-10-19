package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
19.10.2020 17:29

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.InWaterMoveEvent;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e){

        Player p = e.getPlayer();

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        if(user != null){

            if (user.getSelected() != null) {

                if(p.getLocation().getBlock().getType() == Material.WATER){

                    InWaterMoveEvent inWaterMoveEvent = new InWaterMoveEvent(user, p, e.getFrom(), e.getTo());



                    AbilityEventBus.post(inWaterMoveEvent);

                    e.setCancelled(inWaterMoveEvent.isCancelled());
                    e.setTo(inWaterMoveEvent.getTo());

                }

            }

        }

    }

}
