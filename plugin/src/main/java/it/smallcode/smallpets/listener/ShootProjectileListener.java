package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
02.11.2020 19:55

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.ShootBowEvent;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ShootProjectileListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onShoot(EntityShootBowEvent e){

        if(!e.isCancelled()) {

            if(!(e.getProjectile() instanceof Projectile))
                return;

            ShootBowEvent shootProjectileEvent = new ShootBowEvent(e.getEntity(), (Projectile) e.getProjectile(), e.getBow());
            shootProjectileEvent.setCancelled(e.isCancelled());

            AbilityEventBus.post(shootProjectileEvent);

            e.setCancelled(shootProjectileEvent.isCancelled());

        }

    }

}
