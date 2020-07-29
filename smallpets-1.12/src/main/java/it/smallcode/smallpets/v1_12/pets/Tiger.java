package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 19:01

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.v1_12.SkullCreator;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tiger extends it.smallcode.smallpets.v1_15.pets.Tiger {

    /**
     * Creates a pet
     *
     * @param owner           - the pet owner
     * @param xp              - the xp
     * @param useProtocolLib
     * @param languageManager
     */
    public Tiger(Player owner, Long xp, boolean useProtocolLib, LanguageManager languageManager) {
        super(owner, xp, useProtocolLib, languageManager);
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

            PacketContainer teleportPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_TELEPORT);

            teleportPacket.getIntegers().write(0, entityID);

            teleportPacket.getDoubles().write(0, loc.getX());
            teleportPacket.getDoubles().write(1, loc.getY());
            teleportPacket.getDoubles().write(2, loc.getZ());

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

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem(plugin);

        NamespacedKey key = new NamespacedKey(plugin, "pet_tiger");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" M ", "CBR", " P ");

        recipe.setIngredient('M', Material.MUTTON);
        recipe.setIngredient('C', Material.RAW_CHICKEN);
        recipe.setIngredient('B', Material.RAW_BEEF);
        recipe.setIngredient('R', Material.RABBIT);
        recipe.setIngredient('P', Material.PORK);

        Bukkit.addRecipe(recipe);

    }

    @Override
    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM0MjYzODc0NDkyMmI1ZmNmNjJjZDliZjI3ZWVhYjkxYjJlNzJkNmM3MGU4NmNjNWFhMzg4Mzk5M2U5ZDg0In19fQ==");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

    }

    /**
     *
     * Returns the item to unlock the tiger
     *
     * @param plugin - the plugin
     * @return the item to unlock the tiger
     */
    @Override
    public ItemStack getUnlockItem(Plugin plugin){

        ItemStack item = getItem();

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName("§6Tiger");

        ArrayList<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§6RIGHT CLICK TO UNLOCK");

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        item = addNBTTag(item, "pet", getName());

        return item;

    }

    private ItemStack addNBTTag(ItemStack itemStack, String key, String value){

        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();

        tag.setString(key, value);

        stack.setTag(tag);

        itemStack = CraftItemStack.asCraftMirror(stack);

        return itemStack;

    }

}
