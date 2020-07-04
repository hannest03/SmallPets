package it.smallcode.smallpets.pets.v1_15.animation;
/*

Class created by SmallCode
02.07.2020 17:03

*/
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class FollowPlayerArmorStand {

    private int schedulerID;
    private Player player;

    private boolean play = true;

    public FollowPlayerArmorStand(final ArmorStand armorStand, final double speed, Player player, Plugin plugin){

        this.player = player;

        schedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {

                if(play) {

                    Vector goal = vectorFromLocation(player.getLocation());

                    goal.setY(goal.getY() + 0.75);

                    Vector start = vectorFromLocation(armorStand.getLocation());

                    Vector direction = normalize(goal.subtract(start));

                    Location newLoc = armorStand.getLocation().clone();

                    newLoc.add(direction.multiply(speed));

                    try {

                        if(!newLoc.getChunk().isLoaded())
                            newLoc.getChunk().load();

                        if(!armorStand.getLocation().getChunk().isLoaded())
                            armorStand.getLocation().getChunk().load();

                        armorStand.teleport(newLoc);

                    }catch (Exception ex){ ex.printStackTrace(); }

                }

            }
        }, 0, 0);

    }

    private Vector vectorFromLocation(Location location){

        return new Vector(location.getX(), location.getY(), location.getZ());

    }

    private Vector normalize(Vector vec){

        return new Vector(vec.getX() / vec.length(), vec.getY() / vec.length(), vec.getZ() / vec.length());

    }

    public void setActive(boolean active){

        play = active;

    }

    public void cancel(){

        Bukkit.getScheduler().cancelTask(schedulerID);

    }

}
