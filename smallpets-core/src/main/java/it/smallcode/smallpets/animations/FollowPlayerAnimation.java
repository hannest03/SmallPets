package it.smallcode.smallpets.animations;
/*

Class created by SmallCode
11.07.2020 00:43

*/

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class FollowPlayerAnimation {

    protected double speed;

    public FollowPlayerAnimation(double speed){

        this.speed = speed;

    }

    public abstract Location move(Player player, Location loc);

    protected Vector vectorFromLocation(Location location){

        return new Vector(location.getX(), location.getY(), location.getZ());

    }

    protected Vector normalize(Vector vec){

        return new Vector(vec.getX() / vec.length(), vec.getY() / vec.length(), vec.getZ() / vec.length());

    }

}
