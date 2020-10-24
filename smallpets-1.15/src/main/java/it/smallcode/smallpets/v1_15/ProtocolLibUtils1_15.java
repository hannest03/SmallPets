package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
24.10.2020 19:52

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.smallcode.smallpets.core.utils.IProtocolLibUtils;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

public class ProtocolLibUtils1_15 implements IProtocolLibUtils {


    @Override
    public PacketContainer setCustomName(int entityID, String name) {

        PacketContainer entityMetadata = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);

        entityMetadata.getIntegers().write(0, entityID);

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getWatchableCollectionModifier().read(0));

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), Optional
                .of(WrappedChatComponent
                        .fromChatMessage(name)[0].getHandle()));

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

        entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return entityMetadata;

    }

    @Override
    public PacketContainer spawnArmorstand(int entityID, Location location) {

        PacketContainer spawnPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        spawnPacket.getIntegers().write(0, entityID);
        spawnPacket.getIntegers().write(6, 0);

        spawnPacket.getEntityTypeModifier().write(0, EntityType.ARMOR_STAND);

        spawnPacket.getDoubles().write(0, location.getX());
        spawnPacket.getDoubles().write(1, location.getY());
        spawnPacket.getDoubles().write(2, location.getZ());

        spawnPacket.getIntegers().write(4, 0);
        spawnPacket.getIntegers().write(5, (int) (location.getYaw() * 256.0F / 360.0F));

        spawnPacket.getUUIDs().write(0, UUID.randomUUID());

        return spawnPacket;

    }

    @Override
    public PacketContainer equipItem(int entityID, EnumWrappers.ItemSlot itemSlot, ItemStack item) {

        PacketContainer entityEquipment = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        entityEquipment.getIntegers().write(0, entityID);
        entityEquipment.getItemSlots().write(0, itemSlot);
        entityEquipment.getItemModifier().write(0, item);

        return entityEquipment;

    }

    @Override
    public PacketContainer standardMetaData(int entityID) {

        PacketContainer entityMetadata = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);

        entityMetadata.getIntegers().write(0, entityID);

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getWatchableCollectionModifier().read(0));

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20);
        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(14, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x01);

        entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return entityMetadata;

    }

    @Override
    public PacketContainer destroyEntity(int entityID) {

        PacketContainer entityDestroy = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);

        int[] entityIDs = new int[1];

        entityIDs[0] = entityID;

        entityDestroy.getIntegerArrays().write(0, entityIDs);

        return entityDestroy;

    }

    @Override
    public PacketContainer teleportEntity(int entityID, Location location) {

        PacketContainer teleportPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_TELEPORT);

        teleportPacket.getIntegers().write(0, entityID);

        teleportPacket.getDoubles().write(0, location.getX());
        teleportPacket.getDoubles().write(1, location.getY());
        teleportPacket.getDoubles().write(2, location.getZ());

        teleportPacket.getBytes().write(0, (byte) (location.getYaw() * 256.0F / 360.0F));
        teleportPacket.getBytes().write(1, (byte) (location.getPitch() * 256.0F / 360.0F));

        return teleportPacket;

    }
}
