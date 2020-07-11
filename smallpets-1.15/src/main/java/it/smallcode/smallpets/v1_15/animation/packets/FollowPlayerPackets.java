package it.smallcode.smallpets.v1_15.animation.packets;
/*

Class created by SmallCode
02.07.2020 17:03

*/
import com.comphenix.packetwrapper.WrapperPlayClientPosition;
import com.comphenix.packetwrapper.WrapperPlayServerEntityTeleport;
import com.comphenix.packetwrapper.WrapperPlayServerPosition;
import com.comphenix.packetwrapper.WrapperPlayServerRelEntityMove;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import it.smallcode.smallpets.animations.FollowPlayerAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

public class FollowPlayerPackets extends FollowPlayerAnimation {

    private int entityID;

    public FollowPlayerPackets(int entityID, double speed){

        super(speed);

        this.entityID = entityID;

    }

    @Override
    public Location move(Player player, Location loc) {

        Vector goal = vectorFromLocation(player.getLocation());

        goal.setY(goal.getY() + 0.75);

        Vector start = vectorFromLocation(loc);

        Vector direction = normalize(goal.subtract(start));

        Location newLoc = loc.clone();

        newLoc.add(direction.multiply(speed));

        try {

            WrapperPlayServerRelEntityMove teleportPacket = new WrapperPlayServerRelEntityMove();

            teleportPacket.setEntityID(entityID);

            teleportPacket.setDx(newLoc.getX() - loc.getX());
            teleportPacket.setDy(newLoc.getY() - loc.getY());
            teleportPacket.setDz(newLoc.getZ() - loc.getZ());

            sendPacket(teleportPacket.getHandle());

            return newLoc;

        } catch (Exception ex) {

            ex.printStackTrace();

            return loc;

        }

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
