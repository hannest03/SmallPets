package it.smallcode.smallpets.v1_13.pets;
/*

Class created by SmallCode
10.07.2020 15:22

*/

import net.minecraft.server.v1_13_R2.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Tiger extends it.smallcode.smallpets.v1_15.pets.Tiger {

    public Tiger(Player owner, Long xp, Boolean useProtocolLib) {
        super(owner, xp, useProtocolLib);
    }

    @Override
    public ItemStack getUnlockItem(Plugin plugin) {

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

        net.minecraft.server.v1_13_R2.ItemStack item = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tag = item.getTag() != null ? item.getTag() : new NBTTagCompound();

        tag.setString(key, value);

        item.setTag(tag);

        itemStack = CraftItemStack.asCraftMirror(item);

        return itemStack;

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem(plugin);

        NamespacedKey key = new NamespacedKey(plugin, "pet_tiger");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" M ", "CBR", " P ");

        recipe.setIngredient('M', Material.MUTTON);
        recipe.setIngredient('C', Material.CHICKEN);
        recipe.setIngredient('B', Material.BEEF);
        recipe.setIngredient('R', Material.RABBIT);
        recipe.setIngredient('P', Material.PORKCHOP);

        Bukkit.addRecipe(recipe);

    }

}
