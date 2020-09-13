package it.smallcode.smallpets.core.animations;
/*

Class created by SmallCode
18.08.2020 21:23

*/

import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WalkAwayFromPlayerAnimation {

    protected double speed;
    private Pet pet;

    public WalkAwayFromPlayerAnimation(Pet pet, double speed){

        this.pet = pet;
        this.speed = speed;

    }

    public Location move(Player player, Location loc){

        Vector goal = vectorFromLocation(player.getLocation());

        Vector start = vectorFromLocation(loc);

        Vector direction = normalize(goal.subtract(start).multiply(-1));

        Location newLoc = loc.clone();

        newLoc.add(direction.multiply(speed));

        newLoc.setY(loc.getY());

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

    protected Vector vectorFromLocation(Location location){

        return new Vector(location.getX(), location.getY(), location.getZ());

    }

    protected Vector normalize(Vector vec){

        return new Vector(vec.getX() / vec.length(), vec.getY() / vec.length(), vec.getZ() / vec.length());

    }

}
