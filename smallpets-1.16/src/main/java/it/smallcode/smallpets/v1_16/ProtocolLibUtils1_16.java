package it.smallcode.smallpets.v1_16;
/*

Class created by SmallCode
24.10.2020 19:55

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import it.smallcode.smallpets.v1_15.ProtocolLibUtils1_15;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class ProtocolLibUtils1_16 extends ProtocolLibUtils1_15 {

    @Override
    public PacketContainer equipItem(int entityID, EnumWrappers.ItemSlot itemSlot, ItemStack item) {

        PacketContainer entityEquipment = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        entityEquipment.getIntegers().write(0, entityID);

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> equipment = new LinkedList<>();

        equipment.add(new Pair<>(itemSlot, item));

        entityEquipment.getSlotStackPairLists().write(0, equipment);

        return entityEquipment;

    }
}
