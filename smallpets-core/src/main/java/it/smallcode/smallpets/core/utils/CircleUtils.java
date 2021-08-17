package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
07.08.2021 19:39

*/

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class CircleUtils {

    public static void drawCircle(Location location, double radius, double spaceBetween, Particle particle){
        int n = (int) ((2 * Math.PI * radius) / spaceBetween);
        double angle = 2 * Math.PI / n;

        //Get particle positions
        Location[] particleLocations = new Location[n];
        for(int i = 0; i < n; i++){
            double x = location.getX() + (radius * Math.cos(angle * i));
            double z = location.getZ() + (radius * Math.sin(angle * i));
            particleLocations[i] = new Location(location.getWorld(), x, location.getY(), z);
        }

        //Spawn particles to players
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getLocation().getWorld() == location.getWorld()){
                for(Location loc : particleLocations){
                    p.spawnParticle(particle, loc, 0);
                }
            }
        }

    }

}
