package it.smallcode.smallpets.v1_13.animations.packets;
/*

Class created by SmallCode
03.07.2020 14:33

*/

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import it.smallcode.smallpets.animations.HoverAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class HoverPackets extends HoverAnimation {

    private int entityID;

    public HoverPackets(int entityID, final double speed, double maxHeightCap, double minHeightCap){

        super(speed, maxHeightCap, minHeightCap);

        this.entityID = entityID;

    }

    @Override
    public Location hover(Player player, Location loc) {

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

        try {

            return newLoc;

        }catch (Exception ex){ ex.printStackTrace(); }

        return loc;

    }

    private void sendPacket(PacketContainer packet, Player player){

        for(Player all : Bukkit.getOnlinePlayers()){

            if(all.getWorld().getName().equals(player.getWorld().getName()))

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(all, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

    }

}
