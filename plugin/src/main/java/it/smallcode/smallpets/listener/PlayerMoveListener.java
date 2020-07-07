package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
05.07.2020 21:16

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.manager.types.User;
import it.smallcode.smallpets.pets.v1_16.pets.Penguin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e){

        Player p = e.getPlayer();

        if(p.isSwimming()){

            User user = SmallPets.getInstance().getUserManager().getUser(e.getPlayer().getUniqueId().toString());

            if(user != null)
                if(user.getSelected() != null)
                    if(user.getSelected().getName().equalsIgnoreCase("penguin")) {

                            Vector changed = e.getTo().clone().subtract(e.getFrom()).toVector().multiply(1F + ((Penguin.maxSwimmingMultiplier / 100F) * user.getSelected().getLevel()));
                            changed.setY(e.getTo().clone().subtract(e.getFrom()).toVector().getY());

                            float maxSpeed = (0.3F + ((Penguin.maxSwimmingMultiplier / 100F) * user.getSelected().getLevel()));

                            if(Math.abs(changed.getX()) <= maxSpeed && Math.abs(changed.getY()) <= maxSpeed && Math.abs(changed.getZ()) <= maxSpeed) {

                                e.getPlayer().setVelocity(changed);

                            }

                    }

        }

    }


}
