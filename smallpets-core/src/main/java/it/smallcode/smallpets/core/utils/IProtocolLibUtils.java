package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
24.10.2020 19:47

*/

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface IProtocolLibUtils {

    PacketContainer setCustomName(int entityID, String name);
    PacketContainer spawnArmorstand(int entityID, Location location);
    PacketContainer equipItem(int entityID, EnumWrappers.ItemSlot itemSlot, ItemStack item);
    PacketContainer standardMetaData(int entityID);
    PacketContainer destroyEntity(int entityID);
    PacketContainer teleportEntity(int entityID, Location location);

}
