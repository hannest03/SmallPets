package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
14.07.2020 13:41

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class SamplePet extends it.smallcode.smallpets.v1_15.SamplePet {


    /**
     * Creates a pet
     *
     * @param owner          - the pet owner
     * @param xp             - the xp
     * @param useProtocolLib
     */
    public SamplePet(Player owner, Long xp, boolean useProtocolLib) {
        super(owner, xp, useProtocolLib);
    }

    @Override
    protected void spawnArmorstandWithPackets(List<Player> players) {

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        //SPAWN ARMORSTAND

        PacketContainer spawnPacket = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

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

        //EQUIPMENT

        PacketContainer entityEquipment = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        entityEquipment.getIntegers().write(0, entityID);
        entityEquipment.getItemSlots().write(0, EnumWrappers.ItemSlot.HEAD);
        entityEquipment.getItemModifier().write(0, getItem());

        //METADATA

        PacketContainer entityMetadata = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

        entityMetadata.getIntegers().write(0, entityID);

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getWatchableCollectionModifier().read(0));

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20);
        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(11, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x01);

        entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        sendPacket(sendPacketToPlayers(owner), spawnPacket);
        sendPacket(sendPacketToPlayers(owner), entityEquipment);
        sendPacket(sendPacketToPlayers(owner), entityMetadata);



    }

    @Override
    public void setCustomName(String name){

        if(useProtocolLib){

            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

            PacketContainer entityMetadata = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

            entityMetadata.getIntegers().write(0, entityID);

            WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getWatchableCollectionModifier().read(0));

            dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.get(String.class)), name);

            dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

            entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

            sendPacket(sendPacketToPlayers(owner), entityMetadata);

        }else{

            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(name);

        }

    }

    @Override
    public void teleport(Location loc) {

        if(useProtocolLib){

            PacketContainer teleportPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK);

            teleportPacket.getIntegers().write(0, entityID);

            teleportPacket.getIntegers().write(0, (int) ((loc.getX() - location.getX()) * 4096));
            teleportPacket.getIntegers().write(1, (int) ((loc.getY() - location.getY()) * 4096));
            teleportPacket.getIntegers().write(2, (int) ((loc.getZ() - location.getZ()) * 4096));

            teleportPacket.getBytes().write(0, (byte) (loc.getYaw() * 256.0F / 360.0F));
            teleportPacket.getBytes().write(1, (byte) (loc.getPitch() * 256.0F / 360.0F));

            sendPacket(sendPacketToPlayers(owner), teleportPacket);

        }else{

            if(!location.getChunk().isLoaded())
                location.getChunk().load();

            if(!loc.getChunk().isLoaded())
                loc.getChunk().load();

            armorStand.teleport(loc);

        }

        setLocation(loc);

    }

}
