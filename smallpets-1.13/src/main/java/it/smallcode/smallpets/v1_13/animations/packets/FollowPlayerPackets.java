package it.smallcode.smallpets.v1_13.animations.packets;
/*

Class created by SmallCode
02.07.2020 17:03

*/
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import it.smallcode.smallpets.animations.FollowPlayerAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;

public class FollowPlayerPackets extends FollowPlayerAnimation {

    private int entityID;

    public FollowPlayerPackets(int entityID, double speed){

        super(speed);

        this.entityID = entityID;

    }

    @Override
    public Location move(Player player, Location loc) {

        //Move

        Vector goal = vectorFromLocation(player.getLocation());

        goal.setY(goal.getY() + 0.75);

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

        try {



            return newLoc;

        } catch (Exception ex) {

            ex.printStackTrace();

            return loc;

        }

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
