package it.smallcode.smallpets.core.animations;
/*

Class created by SmallCode
11.07.2020 00:43

*/

import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FollowPlayerAnimation {

    protected double speed;
    private Pet pet;

    public FollowPlayerAnimation(Pet pet, double speed){

        this.pet = pet;
        this.speed = speed;

    }

    public Location move(Player player, Location loc){

        Vector goal = vectorFromLocation(player.getLocation());

        goal.setY(goal.getY() + 0.75);

        double distance = Math.sqrt(Math.pow(loc.getX() - player.getLocation().getX(), 2) + Math.pow(loc.getZ() - player.getLocation().getZ(), 2));

        if (distance < 2.5D){

            goal.setY(goal.getY() + player.getLocation().getY() - loc.getY());
            goal.setX(loc.getX());
            goal.setZ(loc.getZ());

        }

        Vector start = vectorFromLocation(loc);

        Vector direction = normalize(goal.subtract(start));

        Location newLoc = loc.clone();

        newLoc.add(direction.multiply(speed));

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
