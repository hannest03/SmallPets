package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
04.07.2020 10:15

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.manager.UserManager;
import org.bukkit.Bukkit;
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

                    }
                });

            }
        });

    }

}
