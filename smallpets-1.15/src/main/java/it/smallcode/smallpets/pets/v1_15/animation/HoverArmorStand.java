package it.smallcode.smallpets.pets.v1_15.animation;
/*

Class created by SmallCode
03.07.2020 14:33

*/

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.Plugin;

public class HoverArmorStand {

    private int schedulerID;

    private double vel;

    private boolean play = true;

    private double height = 0;

    public HoverArmorStand(final ArmorStand armorStand, final double speed, double maxHeightCap, double minHeightCap, Plugin plugin){

        vel = speed;

        schedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {

                if(play) {

                    if (height >= maxHeightCap)
                        vel = -speed;

                    if (height <= minHeightCap)
                        vel = speed;

                    Location newLoc = armorStand.getLocation().clone();

                    height += vel;

                    newLoc.setY(newLoc.getY() + vel);

                    try {

                        if(!armorStand.getLocation().getChunk().isLoaded())
                            armorStand.getLocation().getChunk().load();

                        if(!newLoc.getChunk().isLoaded())
                            newLoc.getChunk().load();

                        armorStand.teleport(newLoc);

                    }catch (Exception ex){ ex.printStackTrace(); }

                }

            }
        }, 0, 0);

    }

    public void setActive(boolean active){

        if(!active)
            height = 0;

        play = active;

    }

    public void cancel(){

        Bukkit.getScheduler().cancelTask(schedulerID);

    }

}
