package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
18.05.2021 10:49

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.utils.ZipUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class BackupManager {

    private long backupLifetimeInMinutes = 43200;

    private long interval = 60;
    private int processID;

    public void start(){

        processID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), new Runnable() {
            @Override
            public void run() {

                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Backup: Starting save...");

                String folder = SmallPetsCommons.getSmallPetsCommons().getJavaPlugin().getDataFolder().toString();

                ZipUtils zipUtils = new ZipUtils();

                //Add files which should be saved

                zipUtils.addFiles(new File(folder + "/users/"));
                zipUtils.addFiles(new File(folder + "/languages/"));
                zipUtils.addFiles(new File(folder + "/config.yml"));
                zipUtils.addFiles(new File(folder + "/experienceTable.yml"));

                Date now = new Date(System.currentTimeMillis());

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");

                //Export to zip file and save
                zipUtils.exportZip(folder + "/backups/" + formatter.format(now) + ".bak.zip", new String[]{ folder + File.separator});

                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Backup: Finished saving!");

                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Backup: Deleting old files...");

                //Loop through all backup files
                for(File file : new File(folder + "/backups/").listFiles()){

                    if(file.isFile()){

                        String timestamp = file.getName().replace(".bak.zip", "");

                        try {

                            Date date = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").parse(timestamp);

                            //Check if timestamp is beyond backup lifetime
                            if(Duration.between(date.toInstant(), Instant.now()).compareTo(Duration.ofMinutes(backupLifetimeInMinutes)) > 0){

                                file.delete();

                            }

                        } catch (ParseException ex) {

                            ex.printStackTrace();

                        }

                    }

                }

                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Backup: Deleted old files!");

            }
        }, 20*60*interval, 20*60*interval);

        Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Backup: Started process");

    }

    public void stop(){

        if(processID != -1) {

            Bukkit.getScheduler().cancelTask(processID);

            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Backup: Stopped process");

        }

    }

    public long getBackupLifetimeInMinutes() {
        return backupLifetimeInMinutes;
    }

    public void setBackupLifetimeInMinutes(long backupLifetimeInMinutes) {
        this.backupLifetimeInMinutes = backupLifetimeInMinutes;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

}
