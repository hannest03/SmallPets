package it.smallcode.smallpets.pets.v1_15.animation;
/*

Class created by SmallCode
02.07.2020 17:03

*/
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class FollowPlayerArmorStand {

    private int schedulerID;
    private Player player;

    private boolean play = true;

    public FollowPlayerArmorStand(final ArmorStand armorStand, final double speed, Player player, Plugin plugin){

        schedulerID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            public void run() {

                if(play) {

                    float yaw = player.getLocation().getYaw() - 180f;

                    Location goal = player.getLocation().clone();

                    goal.setY(goal.getY() + 1);

                    goal = goal.multiply(3);

                    goal.multiply(5);

                    double distance = armorStand.getLocation().distance(goal);



                    Location newLoc = armorStand.getLocation().clone();

                    newLoc.setYaw(yaw);

                    armorStand.teleport(newLoc);

                }

            }
        }, 0, 0);

    }

    private Location normalize(Location loc){

        return new Location(loc.getWorld(), loc.getX() / 3, loc.getY() / 3, loc.getZ() / 3);

    }

    public void toggle(){

        play = !play;

    }

    public void cancel(){

        Bukkit.getScheduler().cancelTask(schedulerID);

    }

}
