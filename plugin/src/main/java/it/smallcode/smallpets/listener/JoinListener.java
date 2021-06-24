package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
04.07.2020 10:15

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.JoinEvent;
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

                userManager.loadUser(e.getPlayer().getUniqueId().toString());

                Bukkit.getScheduler().scheduleSyncDelayedTask(SmallPets.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        User user = userManager.getUser(e.getPlayer().getUniqueId().toString());

                        user.spawnSelected();

                        if(user.getSelected() != null){

                            AbilityEventBus.post(new JoinEvent(user));

                        }

                        for(Player all : Bukkit.getOnlinePlayers()){

                            if(all != e.getPlayer()) {

                                if (all.getWorld().getName().equals(e.getPlayer().getWorld().getName())) {

                                   user = userManager.getUser(all.getUniqueId().toString());

                                    if (user != null && user.getSelected() != null) {

                                        user.getSelected().spawnToPlayer(e.getPlayer());

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
