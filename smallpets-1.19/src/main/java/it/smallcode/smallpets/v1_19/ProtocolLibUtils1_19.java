package it.smallcode.smallpets.v1_19;
/*

Class created by SmallCode
10.06.2022 21:59

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import it.smallcode.smallpets.v1_18.ProtocolLibUtils1_18;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class ProtocolLibUtils1_19 extends ProtocolLibUtils1_18 {
    @Override
    public PacketContainer spawnArmorstand(int entityID, Location location) {
        PacketContainer spawnPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        spawnPacket.getIntegers().write(0, entityID);

        spawnPacket.getEntityTypeModifier().write(0, EntityType.ARMOR_STAND);

        spawnPacket.getDoubles().write(0, location.getX());
        spawnPacket.getDoubles().write(1, location.getY());
        spawnPacket.getDoubles().write(2, location.getZ());

        spawnPacket.getIntegers().write(3, 0);
        spawnPacket.getIntegers().write(4, (int) (location.getYaw() * 256.0F / 360.0F));

        spawnPacket.getUUIDs().write(0, UUID.randomUUID());

        return spawnPacket;
    }
}
