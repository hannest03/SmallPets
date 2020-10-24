package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
24.10.2020 19:57

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.smallcode.smallpets.v1_15.ProtocolLibUtils1_15;
import org.bukkit.Location;

import java.util.UUID;

public class ProtocolLibUtils1_12 extends ProtocolLibUtils1_15 {

    @Override
    public PacketContainer spawnArmorstand(int entityID, Location location) {

        PacketContainer spawnPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        spawnPacket.getIntegers().write(0, entityID);

        //Entity type
        spawnPacket.getIntegers().write(6, 78);

        //Object data
        spawnPacket.getIntegers().write(7, 0);

        spawnPacket.getDoubles().write(0, location.getX());
        spawnPacket.getDoubles().write(1, location.getY());
        spawnPacket.getDoubles().write(2, location.getZ());

        spawnPacket.getIntegers().write(4, 0);
        spawnPacket.getIntegers().write(5, (int) (location.getYaw() * 256.0F / 360.0F));

        spawnPacket.getUUIDs().write(0, UUID.randomUUID());

        return spawnPacket;

    }

    @Override
    public PacketContainer setCustomName(int entityID, String name) {

        PacketContainer entityMetadata = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);

        entityMetadata.getIntegers().write(0, entityID);

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getWatchableCollectionModifier().read(0));

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.get(String.class)), name);

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

        entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return entityMetadata;

    }

    @Override
    public PacketContainer standardMetaData(int entityID) {

        PacketContainer entityMetadata = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);

        entityMetadata.getIntegers().write(0, entityID);

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getWatchableCollectionModifier().read(0));

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20);
        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(11, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x01);

        entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return entityMetadata;

    }

}
