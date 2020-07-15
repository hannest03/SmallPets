package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
04.07.2020 13:42

*/

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import it.smallcode.smallpets.events.PetChangeWorldEvent;
import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class WorldChangeListener implements Listener {

    private JavaPlugin plugin;

    private UserManager userManager;

    private boolean useProtocollib;

    public WorldChangeListener(UserManager userManager, JavaPlugin plugin, boolean useProtocollib){

        this.userManager = userManager;

        this.plugin = plugin;

        this.useProtocollib = useProtocollib;

    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e){

        User user = userManager.getUser(e.getPlayer().getUniqueId().toString());

        if(user != null){

            if(user.getSelected() != null){

                PetChangeWorldEvent event = new PetChangeWorldEvent(user.getSelected(), e.getPlayer());

                Bukkit.getPluginManager().callEvent(event);

                if(!event.isCancelled()) {

                    if(useProtocollib) {

                        for (Player all : Bukkit.getOnlinePlayers()) {

                            if (all.getWorld().getName().equals(e.getFrom().getName())) {

                                user.getSelected().despawnFromPlayer(all, plugin);

                            }

                            if (all.getWorld().getName().equals(e.getPlayer().getWorld().getName())) {

                                user.getSelected().spawnToPlayer(all, plugin);

                                User userAll = userManager.getUser(all.getUniqueId().toString());

                                if (userAll != null && userAll.getSelected() != null) {

                                    userAll.getSelected().spawnToPlayer(e.getPlayer(), plugin);

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

    private void sendPacket(PacketContainer packet, World world){

        for(Player all : Bukkit.getOnlinePlayers()){

            if(all.getWorld().getName().equals(world.getName()))

                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(all, packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

        }

    }

    private void loadChunksInRange(int x, int z, World world){

        if(!world.getChunkAt(x, z).isLoaded())
            world.getChunkAt(x, z).load();

        if(!world.getChunkAt(x +1, z).isLoaded())
            world.getChunkAt(x +1, z).load();

        if(!world.getChunkAt(x -1, z).isLoaded())
            world.getChunkAt(x -1, z).load();

        if(!world.getChunkAt(x, z +1).isLoaded())
            world.getChunkAt(x, z +1).load();

        if(!world.getChunkAt(x, z -1).isLoaded())
            world.getChunkAt(x, z -1).load();

        if(!world.getChunkAt(x, z).isLoaded())
            world.getChunkAt(x, z).load();

        if(!world.getChunkAt(x +1, z -1).isLoaded())
            world.getChunkAt(x +1, z -1).load();

        if(!world.getChunkAt(x -1, z +1).isLoaded())
            world.getChunkAt(x -1, z +1).load();

        if(!world.getChunkAt(x +1, z +1).isLoaded())
            world.getChunkAt(x +1, z +1).load();

        if(!world.getChunkAt(x -1, z -1).isLoaded())
            world.getChunkAt(x -1, z -1).load();

    }

}
