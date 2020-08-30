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
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.text.CenteredText;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SamplePet extends it.smallcode.smallpets.v1_15.SamplePet {

    /**
     * Creates a pet
     *
     * @param owner           - the pet owner
     * @param xp              - the xp
     * @param useProtocolLib
     * @param languageManager
     */
    public SamplePet(Player owner, Long xp, boolean useProtocolLib, LanguageManager languageManager) {
        super(owner, xp, useProtocolLib, languageManager);
    }

    /**
     *
     * Returns the item to unlock the tiger
     *
     * @param plugin - the plugin
     * @return the item to unlock the tiger
     */
    @Override
    public ItemStack getUnlockItem(Plugin plugin) {

        ItemStack item = getDisplayItem((JavaPlugin) plugin);

        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = itemMeta.getLore();

        lore.add("");
        lore.add("§6RIGHT CLICK TO UNLOCK");

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        item = addNBTTag(item, "petExp", String.valueOf(getXp()));

        return item;

    }

    @Override
    public ItemStack getDisplayItem(JavaPlugin plugin) {

        ItemStack itemStack = getItem();

        if(itemStack != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(getCustomeNameWithoutPlayername());

            ArrayList<String> lore = new ArrayList();

            if(getPetType() != null) {

                lore.add("§8" + getPetType().getName(getLanguageManager()));

            }

            lore.add("");

            lore.add(getAbility());

            lore.add("");

            String progressBar = CenteredText.sendCenteredMessage(generateFinishedProgressbar(), ChatColor.stripColor(getAbility()).length());

            if(getLevel() != 100) {

                lore.add("  " + CenteredText.sendCenteredMessage(getLevelColor() + getLevel(), ChatColor.stripColor(progressBar).length()));
                lore.add(progressBar);

                String expB = getLevelColor() + (getXp() - getExpForLevel(getLevel())) + "§8/" + getLevelColor() + (getExpForNextLevel() - getExpForLevel(getLevel()));

                lore.add("  " + CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

            }else{

                lore.add("§8" + getLanguageManager().getLanguage().getStringFormatted("maxLevel"));

            }

            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);

            itemStack = addNBTTag(itemStack, "pet", getName());

        }

        return itemStack;

    }

    private ItemStack addNBTTag(ItemStack itemStack, String key, String value){

        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();

        tag.setString(key, value);

        stack.setTag(tag);

        itemStack = CraftItemStack.asCraftMirror(stack);

        return itemStack;

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
