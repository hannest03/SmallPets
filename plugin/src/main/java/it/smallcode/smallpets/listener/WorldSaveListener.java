package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
14.07.2020 21:56

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

public class WorldSaveListener implements Listener {

    private static int taskID;

    @EventHandler
    public void onSave(WorldSaveEvent e){

        if(!SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()) {

            SmallPets.getInstance().getUserManager().despawnPets();

            Bukkit.getScheduler().cancelTask(taskID);

            taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(SmallPets.getInstance(), new Runnable() {
                @Override
                public void run() {

                    SmallPets.getInstance().getUserManager().spawnPets();

                }
            }, 20 * 10);

        }

    }

}
