package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
04.07.2020 10:15

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.core.manager.PetMapManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private UserManager userManager;
    private PetMapManager petMapManager;

    public JoinListener(UserManager userManager, PetMapManager petMapManager){

        this.userManager = userManager;
        this.petMapManager = petMapManager;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPets.getInstance(), new Runnable() {
            @Override
            public void run() {

                userManager.loadUser(e.getPlayer().getUniqueId().toString(), petMapManager);

                Bukkit.getScheduler().scheduleSyncDelayedTask(SmallPets.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        userManager.getUser(e.getPlayer().getUniqueId().toString()).spawnSelected();

                        for(Player all : Bukkit.getOnlinePlayers()){

                            if(all != e.getPlayer()) {

                                if (all.getWorld().getName().equals(e.getPlayer().getWorld().getName())) {

                                    User user = userManager.getUser(all.getUniqueId().toString());

                                    if (user != null && user.getSelected() != null) {

                                        user.getSelected().spawnToPlayer(e.getPlayer(), SmallPets.getInstance());

                                    }

                                }

                            }

                        }

                    }
                });

            }
        });

    }

}
