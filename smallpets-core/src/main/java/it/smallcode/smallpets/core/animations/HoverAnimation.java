package it.smallcode.smallpets.core.animations;
/*

Class created by SmallCode
11.07.2020 20:05

*/

import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HoverAnimation {

    protected double speed;

    private Pet pet;

    protected double vel;

    protected double maxHeightCap;
    protected double minHeightCap;

    protected double height;

    public HoverAnimation(Pet pet, double speed, double maxHeightCap, double minHeightCap) {

        this.pet = pet;

        this.speed = speed;
        this.vel = speed;
        this.maxHeightCap = maxHeightCap;
        this.minHeightCap = minHeightCap;
        this.height = 0;

    }

    public Location hover(Player player, Location loc){

        //Hover

        if (height >= maxHeightCap)
            vel = -speed;

        if (height <= minHeightCap)
            vel = speed;

        Location newLoc = loc.clone();

        height += vel;

        newLoc.setY(newLoc.getY() + vel);

        //Rotation

        double a = player.getLocation().getX() - newLoc.getX();
        double b = player.getLocation().getZ() - newLoc.getZ();

        double angle = Math.atan(b / a);

        angle = angle * (180 / Math.PI);

        if(player.getLocation().getX() - newLoc.getX() >= 0){

            angle += 180;

        }

        angle += 90;

        newLoc.setYaw((float) angle);

        pet.teleport(newLoc);

        return newLoc;

    }

}
