package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
19.10.2020 17:29

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerMoveListener implements Listener {

    private ArrayList<Player> inWater = new ArrayList<>();

    private HashMap<Player, Biome> biomeHashMap = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e){

        Player p = e.getPlayer();

        /*

        //Skip if worldguard isn't activated or the flag is deactivated
        if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard() && !WorldGuardImp.checkStateFlag(p, SmallFlags.ALLOW_ABILITIES))
            return;

        */

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        if(user != null){

            if (user.getSelected() != null) {

                if (!e.isCancelled()) {

                    if (p.getLocation().getBlock().getType() == Material.WATER) {

                        InWaterMoveEvent inWaterMoveEvent = new InWaterMoveEvent(user, p, e.getFrom(), e.getTo());
                        inWaterMoveEvent.setCancelled(e.isCancelled());

                        AbilityEventBus.post(inWaterMoveEvent);

                        e.setCancelled(inWaterMoveEvent.isCancelled());
                        e.setTo(inWaterMoveEvent.getTo());

                        if (!inWater.contains(e.getPlayer())) {

                            inWater.add(e.getPlayer());

                            EnterWaterEvent enterWaterEvent = new EnterWaterEvent(user, p.getPlayer());
                            AbilityEventBus.post(enterWaterEvent);

                        }

                    } else {

                        if (inWater.contains(e.getPlayer())) {

                            inWater.remove(e.getPlayer());

                            ExitWaterEvent exitWaterEvent = new ExitWaterEvent(user, p.getPlayer());
                            AbilityEventBus.post(exitWaterEvent);

                        }

                    }

                    if (!biomeHashMap.containsKey(e.getPlayer())) {
                        biomeHashMap.put(e.getPlayer(), e.getTo().getBlock().getBiome());
                        EnterBiomeEvent enterBiomeEvent = new EnterBiomeEvent(user, e.getPlayer(), e.getTo().getBlock().getBiome());
                        AbilityEventBus.post(enterBiomeEvent);
                    }

                    if(biomeHashMap.get(e.getPlayer()) != e.getTo().getBlock().getBiome()) {

                        Biome from = biomeHashMap.get(e.getPlayer());
                        ExitBiomeEvent exitBiomeEvent = new ExitBiomeEvent(user, e.getPlayer(), from);
                        AbilityEventBus.post(exitBiomeEvent);

                        EnterBiomeEvent enterBiomeEvent = new EnterBiomeEvent(user, e.getPlayer(), e.getTo().getBlock().getBiome());
                        AbilityEventBus.post(enterBiomeEvent);

                        biomeHashMap.put(e.getPlayer(), e.getTo().getBlock().getBiome());

                    }

                }

            }

        }

    }

}
