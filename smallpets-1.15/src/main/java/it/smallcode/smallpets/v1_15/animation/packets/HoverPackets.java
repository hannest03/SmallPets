package it.smallcode.smallpets.v1_15.animation.packets;
/*

Class created by SmallCode
03.07.2020 14:33

*/

import com.comphenix.packetwrapper.WrapperPlayServerRelEntityMove;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import it.smallcode.smallpets.animations.HoverAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

public class HoverPackets extends HoverAnimation {

    private int entityID;

    public HoverPackets(int entityID, final double speed, double maxHeightCap, double minHeightCap){

        super(speed, maxHeightCap, minHeightCap);

        this.entityID = entityID;

    }

    @Override
    public Location hover(Player player, Location loc) {

        if (height >= maxHeightCap)
            vel = -speed;

        if (height <= minHeightCap)
            vel = speed;

        Location newLoc = loc.clone();

        height += vel;

        newLoc.setY(newLoc.getY() + vel);

        try {

            WrapperPlayServerRelEntityMove teleportPacket = new WrapperPlayServerRelEntityMove();

            teleportPacket.setEntityID(entityID);

            teleportPacket.setDx(newLoc.getX() - loc.getX());
            teleportPacket.setDy(newLoc.getY() - loc.getY());
            teleportPacket.setDz(newLoc.getZ() - loc.getZ());

            sendPacket(teleportPacket.getHandle());

            return newLoc;

        }catch (Exception ex){ ex.printStackTrace(); }

        return loc;

    }

    private void sendPacket(PacketContainer packet){

        for(Player all : Bukkit.getOnlinePlayers()){

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(all, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

    }

}
