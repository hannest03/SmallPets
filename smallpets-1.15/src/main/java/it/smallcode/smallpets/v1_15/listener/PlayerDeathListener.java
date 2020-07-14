package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
13.07.2020 13:28

*/

import com.comphenix.protocol.ProtocolLibrary;
import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class PlayerDeathListener implements Listener {

    private JavaPlugin plugin;

    private UserManager userManager;

    public PlayerDeathListener(UserManager userManager, JavaPlugin plugin){

        this.userManager = userManager;

        this.plugin = plugin;

    }

    @EventHandler
    public void onDeath(PlayerRespawnEvent e){

        User user = userManager.getUser(e.getPlayer().getUniqueId().toString());

        if(user != null && user.getSelected() != null){

            Location loc = e.getPlayer().getLocation().clone();

            loc.setX(loc.getX() - 1);
            loc.setY(loc.getY() + 0.75);

            user.getSelected().spawnToPlayer(e.getPlayer(), plugin);

            user.getSelected().teleport(loc);

        }

        for(Player all : Bukkit.getOnlinePlayers()){

            if(all != e.getPlayer()) {

                if (all.getWorld().getName().equals(e.getPlayer().getWorld().getName())) {

                    User userAll = userManager.getUser(all.getUniqueId().toString());

                    if (userAll != null && userAll.getSelected() != null) {

                        userAll.getSelected().spawnToPlayer(e.getPlayer(), plugin);

                    }

                }

            }

        }

    }

}
