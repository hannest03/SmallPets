package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
11.10.2020 19:26

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.DamageEvent;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent e){

        if(e.getDamager() instanceof Player){

            Player p = (Player) e.getDamager();

            /*

            //Skip if worldguard isn't activated or the flag is deactivated
            if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard() && !WorldGuardImp.checkStateFlag(p, SmallFlags.ALLOW_ABILITIES))
                return;

             */

            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

            if(user != null){

                if(user.getSelected() != null){

                    if(!e.isCancelled()) {

                        DamageEvent damageEvent = new DamageEvent(user, e.getEntity(), e.getDamage());
                        damageEvent.setCancelled(e.isCancelled());

                        AbilityEventBus.post(damageEvent);

                        e.setDamage(damageEvent.getDamage());
                        e.setCancelled(damageEvent.isCancelled());

                    }

                }

            }

        }

    }

}
