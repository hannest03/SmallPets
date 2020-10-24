package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
13.07.2020 13:28

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerRespawnEvent e){

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(e.getPlayer().getUniqueId().toString());

        if(user != null && user.getSelected() != null){

            Location loc = e.getPlayer().getLocation().clone();

            loc.setX(loc.getX() - 1);
            loc.setY(loc.getY() + 0.75);

            user.getSelected().spawnToPlayer(e.getPlayer(), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

            user.getSelected().setPauseLogic(true);

            user.getSelected().teleport(loc);

            user.getSelected().setPauseLogic(false);

        }

        for(Player all : Bukkit.getOnlinePlayers()){

            if(all != e.getPlayer()) {

                if (all.getWorld().getName().equals(e.getPlayer().getWorld().getName())) {

                    User userAll = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(all.getUniqueId().toString());

                    if (userAll != null && userAll.getSelected() != null) {

                        userAll.getSelected().spawnToPlayer(e.getPlayer(), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                    }

                }

            }

        }

    }

}
