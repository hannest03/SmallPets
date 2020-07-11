package it.smallcode.smallpets.v1_15.animation.armorstands;
/*

Class created by SmallCode
03.07.2020 14:33

*/

import it.smallcode.smallpets.animations.HoverAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HoverArmorStand extends HoverAnimation {

    private ArmorStand armorStand;

    public HoverArmorStand(final ArmorStand armorStand, final double speed, double maxHeightCap, double minHeightCap){

        super(speed, maxHeightCap, minHeightCap);

        this.armorStand = armorStand;

    }

    @Override
    public Location hover(Player player, Location loc) {

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

            return newLoc;

        }catch (Exception ex){ ex.printStackTrace(); return loc; }

    }
}
