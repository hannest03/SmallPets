package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
15.12.2020 20:16

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AutoSaveManager {

    private final Logger logger = SmallPetsCommons.getSmallPetsCommons().getLogger();

    private long interval = 10;
    private int processID;

    public void start(){

        processID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), new Runnable() {
            @Override
            public void run() {

                logger.println("Auto Save: Starting save...");

                UserManager userManager = SmallPetsCommons.getSmallPetsCommons().getUserManager();

                userManager.saveUsers();
                userManager.getUsers().removeIf(user -> !Bukkit.getOfflinePlayer(UUID.fromString(user.getUuid())).isOnline());

                logger.println("Auto Save: Finished saving!");

            }
        }, 20*60*interval, 20*60*interval);

        logger.println("Auto Save: Started process");

    }

    public void stop(){

        if(processID != -1) {

            Bukkit.getScheduler().cancelTask(processID);

            logger.println("Auto Save: Stopped process");

        }

    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
