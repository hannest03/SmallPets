package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
19.10.2020 17:29

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.EnterWaterEvent;
import it.smallcode.smallpets.core.abilities.eventsystem.events.ExitWaterEvent;
import it.smallcode.smallpets.core.abilities.eventsystem.events.InWaterMoveEvent;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class PlayerMoveListener implements Listener {

    private ArrayList<Player> inWater = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e){

        Player p = e.getPlayer();

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        if(user != null){

            if (user.getSelected() != null) {

                if(p.getLocation().getBlock().getType() == Material.WATER){

                    InWaterMoveEvent inWaterMoveEvent = new InWaterMoveEvent(user, p, e.getFrom(), e.getTo());
                    inWaterMoveEvent.setCancelled(e.isCancelled());

                    AbilityEventBus.post(inWaterMoveEvent);

                    e.setCancelled(inWaterMoveEvent.isCancelled());
                    e.setTo(inWaterMoveEvent.getTo());

                    if(!inWater.contains(e.getPlayer())){

                        inWater.add(e.getPlayer());

                        EnterWaterEvent enterWaterEvent = new EnterWaterEvent(user, p.getPlayer());
                        AbilityEventBus.post(enterWaterEvent);

                    }

                }else{

                    if(inWater.contains(e.getPlayer())){

                        inWater.remove(e.getPlayer());

                        ExitWaterEvent exitWaterEvent = new ExitWaterEvent(user, p.getPlayer());
                        AbilityEventBus.post(exitWaterEvent);

                    }

                }

            }

        }

    }

}
