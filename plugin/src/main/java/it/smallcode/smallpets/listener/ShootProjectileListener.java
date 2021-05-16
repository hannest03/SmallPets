package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
02.11.2020 19:55

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.ShootBowEvent;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ShootProjectileListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onShoot(EntityShootBowEvent e){

        /*
        if(e.getEntity() instanceof Player)

            //Skip if worldguard isn't activated or the flag is deactivated
            if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard() && !WorldGuardImp.checkStateFlag(p, SmallFlags.ALLOW_ABILITIES))
                return;

        */

        if(!e.isCancelled()) {

            if(!(e.getProjectile() instanceof Projectile))
                return;
            if(!(e.getEntity() instanceof Player))
                return;

            Player p = (Player) e.getEntity();

            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

            if(user == null)
                return;

            ShootBowEvent shootProjectileEvent = new ShootBowEvent(user, e.getEntity(), (Projectile) e.getProjectile(), e.getBow());
            shootProjectileEvent.setCancelled(e.isCancelled());

            AbilityEventBus.post(shootProjectileEvent);

            e.setCancelled(shootProjectileEvent.isCancelled());

        }

    }

}
