package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
04.07.2020 13:42

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.events.PetChangedWorldEvent;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener implements Listener {

    private boolean useProtocollib;

    public WorldChangeListener(boolean useProtocollib){

        this.useProtocollib = useProtocollib;

    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e){

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(e.getPlayer().getUniqueId().toString());

        if(user != null){

            if(user.getSelected() != null){

                PetChangedWorldEvent event = new PetChangedWorldEvent(user.getSelected(), e.getPlayer());

                Bukkit.getPluginManager().callEvent(event);

                if(user.getSelected() == null)
                    return;

                if(!event.isCancelled()) {

                    if(useProtocollib) {

                        for (Player all : Bukkit.getOnlinePlayers()) {

                            if (all.getWorld().getName().equals(e.getFrom().getName())) {

                                user.getSelected().despawnFromPlayer(all, SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                            }

                            if (all.getWorld().getName().equals(e.getPlayer().getWorld().getName())) {

                                user.getSelected().spawnToPlayer(all, SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                                User userAll = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(all.getUniqueId().toString());

                                if (userAll != null && userAll.getSelected() != null) {

                                    userAll.getSelected().spawnToPlayer(e.getPlayer(), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                                }

                            }

                        }

                    }

                    Location loc = e.getPlayer().getLocation().clone();

                    loc.setX(loc.getX() - 1);
                    loc.setY(loc.getY() + 0.75);

                    user.getSelected().setPauseLogic(true);

                    user.getSelected().teleport(loc);

                    user.getSelected().setPauseLogic(false);

                }else{
                    user.getSelected().destroy();
                }
            }

        }

    }

}
