package it.smallcode.smallpets.animations;
/*

Class created by SmallCode
11.07.2020 20:05

*/

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class HoverAnimation {

    protected double speed;

    protected double vel;

    protected double maxHeightCap;
    protected double minHeightCap;

    protected double height;

    public HoverAnimation(double speed, double maxHeightCap, double minHeightCap) {

        this.speed = speed;
        this.vel = speed;
        this.maxHeightCap = maxHeightCap;
        this.minHeightCap = minHeightCap;
        this.height = 0;

    }

    public abstract Location hover(Player player, Location loc);

}
