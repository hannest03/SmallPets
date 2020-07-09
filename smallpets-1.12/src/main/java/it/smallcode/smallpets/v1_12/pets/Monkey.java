package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 21:49

*/

import it.smallcode.smallpets.v1_12.SkullCreator;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Monkey extends it.smallcode.smallpets.v1_15.pets.Monkey {

    public Monkey(Player owner, Long xp) {
        super(owner, xp);
    }

    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNkNGExNTYwM2Y5NTFkZTJlMmFiODBlZWQxNmJiYjVhNTgyM2JmNGFjYjhjNDYzMzQyNWQ1NDIxMGNmMGFkNSJ9fX0=");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

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
