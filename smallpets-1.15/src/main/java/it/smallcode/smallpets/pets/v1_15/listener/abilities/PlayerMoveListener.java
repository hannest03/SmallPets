package it.smallcode.smallpets.pets.v1_15.listener.abilities;
/*

Class created by SmallCode
05.07.2020 21:16

*/

import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.manager.types.User;
import it.smallcode.smallpets.pets.v1_15.pets.Monkey;
import it.smallcode.smallpets.pets.v1_15.pets.Penguin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {

    private UserManager userManager;

    public PlayerMoveListener(UserManager userManager){

        this.userManager = userManager;

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){

        Player p = e.getPlayer();

        User user = userManager.getUser(e.getPlayer().getUniqueId().toString());

        if(user != null){
            if(user.getSelected() != null){

                if(p.isSwimming()) {

                    if (user.getSelected().getName().equalsIgnoreCase("penguin")) {

                        Vector changed = e.getTo().clone().subtract(e.getFrom()).toVector().multiply(1F + ((Penguin.MAXSWIMMINGSPEED / 100F) * user.getSelected().getLevel()));
                        changed.setY(e.getTo().clone().subtract(e.getFrom()).toVector().getY());

                        float maxSpeed = (0.3F + ((Penguin.MAXSWIMMINGSPEED / 100F) * user.getSelected().getLevel()));

                        if (Math.abs(changed.getX()) <= maxSpeed && Math.abs(changed.getY()) <= maxSpeed && Math.abs(changed.getZ()) <= maxSpeed) {

                            e.getPlayer().setVelocity(changed);

                        }

                    }

                }else{

                    if(user.getSelected().getName().equals("monkey")) {

                        if (p.getVelocity().getY() > 0) {
                            double jumpVelocity = (double) 0.42F;
                            PotionEffect jumpPotion = p.getPotionEffect(PotionEffectType.JUMP);
                            if (jumpPotion != null) {

                                jumpVelocity += (double) ((float) jumpPotion.getAmplifier() + 1) * 0.1F;

                            }

                            if (p.getLocation().getBlock().getType() != Material.LADDER &&
                                    p.getLocation().getBlock().getType() != Material.VINE &&
                                    Double.compare(p.getVelocity().getY(), jumpVelocity) == 0) {

                                Vector velocity = p.getVelocity();

                                velocity.setY(velocity.getY() * ((Monkey.MAXJUMPHEIGHT / 100D) * user.getSelected().getLevel() + 1));

                                p.setVelocity(velocity);

                            }
                        }

                    }

                }

            }

        }

    }


}
