package it.smallcode.smallpets.v1_17;
/*

Class created by SmallCode
11.06.2021 20:20

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.smallcode.smallpets.v1_16.ProtocolLibUtils1_16;

import java.util.List;
import java.util.Optional;

public class ProtocolLibUtils1_17 extends ProtocolLibUtils1_16 {

    @Override
    public PacketContainer standardMetaData(int entityID) {

        PacketContainer entityMetadata = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);

        entityMetadata.getIntegers().write(0, entityID);

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher();

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20);
        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(15, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x01);

        entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return entityMetadata;

    }

    @Override
    public PacketContainer setCustomName(int entityID, String name) {

        PacketContainer entityMetadata = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);

        entityMetadata.getIntegers().write(0, entityID);

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher();

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), Optional
                .of(WrappedChatComponent
                        .fromChatMessage(name)[0].getHandle()));

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

        entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        return entityMetadata;

    }

    @Override
    public PacketContainer destroyEntity(int entityID) {

        PacketContainer entityDestroy = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);

        try {
            List<Integer> entityIDs = entityDestroy.getIntLists().readSafely(0);
            entityIDs.add(entityID);
            entityDestroy.getIntLists().writeSafely(0, entityIDs);
        }catch (Exception ex){
            entityDestroy.getIntegers().write(0, entityID);
        }


        return entityDestroy;

    }

}
