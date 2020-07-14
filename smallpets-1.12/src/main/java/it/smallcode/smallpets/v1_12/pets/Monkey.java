package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 21:49

*/

import com.comphenix.packetwrapper.WrapperPlayServerEntityEquipment;
import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntity;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.v1_12.SkullCreator;
import it.smallcode.smallpets.v1_15.animation.LevelOnehundretAnimation;
import it.smallcode.smallpets.v1_15.animation.packets.FollowPlayerPackets;
import it.smallcode.smallpets.v1_15.animation.packets.HoverPackets;
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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Monkey extends it.smallcode.smallpets.v1_15.pets.Monkey {

    public Monkey(Player owner, Long xp, Boolean useProtocolLib) {
        super(owner, xp, useProtocolLib);
    }

    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNkNGExNTYwM2Y5NTFkZTJlMmFiODBlZWQxNmJiYjVhNTgyM2JmNGFjYjhjNDYzMzQyNWQ1NDIxMGNmMGFkNSJ9fX0=");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

    }

    @Override
    public void spawnPackets(JavaPlugin plugin, Location loc) {

        do{

            entityID = (int) (Math.random() * 10000);

        }while(entityIDs.contains(entityID) && entityID >= 0);

        final Pet pet = this;

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {

                WrapperPlayServerSpawnEntity spawnEntity = new WrapperPlayServerSpawnEntity();

                spawnEntity.setEntityID(entityID);

                spawnEntity.setType(78);
                spawnEntity.setObjectData(0);

                spawnEntity.setX(loc.getX());
                spawnEntity.setY(loc.getY());
                spawnEntity.setZ(loc.getZ());

                sendPacket(spawnEntity.getHandle(), owner);

                WrapperPlayServerEntityEquipment entityEquipment = new WrapperPlayServerEntityEquipment();

                entityEquipment.setEntityID(entityID);
                entityEquipment.setSlot(EnumWrappers.ItemSlot.HEAD);
                entityEquipment.setItem(getItem());

                sendPacket(entityEquipment.getHandle(), owner);

                WrapperPlayServerEntityMetadata entityMetadata = new WrapperPlayServerEntityMetadata();

                entityMetadata.setEntityID(entityID);

                WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getMetadata());

                dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20);
                dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(14, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x01);

                entityMetadata.setMetadata(dataWatcher.getWatchableObjects());

                sendPacket(entityMetadata.getHandle(), owner);

                setCustomName(getCustomeName());

                followPlayerArmorStand = new FollowPlayerPackets(entityID, 0.5D);

                hoverAnimation = new HoverPackets(entityID, 0.025, 0.2, -0.5);

                if(getLevel() == 100)
                    levelOnehundretAnimation = new LevelOnehundretAnimation(pet, plugin);

                rotateID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
                    @Override
                    public void run() {

                        if(Math.abs(location.distance(owner.getLocation())) >= 2.5D)
                            move();
                        else
                            idle();


                    }
                }, 0, 0);

            }
        });

    }

    @Override
    public void spawnToPlayer(Player p, JavaPlugin plugin) {

        if(useProtocolLib){

            Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {

                    WrapperPlayServerSpawnEntity spawnEntity = new WrapperPlayServerSpawnEntity();

                    spawnEntity.setEntityID(entityID);

                    spawnEntity.setType(78);
                    spawnEntity.setObjectData(0);

                    spawnEntity.setX(location.getX());
                    spawnEntity.setY(location.getY());
                    spawnEntity.setZ(location.getZ());

                    spawnEntity.sendPacket(p);

                    WrapperPlayServerEntityEquipment entityEquipment = new WrapperPlayServerEntityEquipment();

                    entityEquipment.setEntityID(entityID);
                    entityEquipment.setSlot(EnumWrappers.ItemSlot.HEAD);
                    entityEquipment.setItem(getItem());

                    entityEquipment.sendPacket(p);

                    WrapperPlayServerEntityMetadata entityMetadata = new WrapperPlayServerEntityMetadata();

                    entityMetadata.setEntityID(entityID);

                    WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getMetadata());

                    dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20);
                    dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(14, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x01);

                    entityMetadata.setMetadata(dataWatcher.getWatchableObjects());

                    entityMetadata.sendPacket(p);

                    setCustomName(getCustomeName());

                }
            });

        }

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem(plugin);

        NamespacedKey key = new NamespacedKey(plugin, "pet_monkey");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" L ", "CLC", " L ");

        recipe.setIngredient('C', Material.getMaterial(351), (short) 3);
        recipe.setIngredient('L', Material.LEATHER);

        Bukkit.addRecipe(recipe);

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

        itemMeta.setDisplayName("§6Monkey");

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
