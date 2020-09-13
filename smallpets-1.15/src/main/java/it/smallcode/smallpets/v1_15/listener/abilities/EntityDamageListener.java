package it.smallcode.smallpets.v1_15.listener.abilities;
/*

Class created by SmallCode
07.07.2020 14:29

*/

import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.v1_15.pets.Tiger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    private UserManager userManager;

    public EntityDamageListener(UserManager userManager){

        this.userManager = userManager;

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){

        if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){

            if(e.getDamager() instanceof Player){

                Player p = (Player) e.getDamager();

                User user = userManager.getUser(p.getUniqueId().toString());

                if(user != null){

                    if(user.getSelected() != null && user.getSelected().getName().equals("tiger")){

                        e.setDamage(e.getDamage() * ((Tiger.MAXDAMAGEMULTIPLIER / 100D) * user.getSelected().getLevel() + 1));

                    }

                }

            }

        }

    }

}
