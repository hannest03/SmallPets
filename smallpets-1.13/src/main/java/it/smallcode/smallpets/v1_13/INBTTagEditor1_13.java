package it.smallcode.smallpets.v1_13;
/*

Class created by SmallCode
24.10.2020 13:42

*/

import it.smallcode.smallpets.core.utils.INBTTagEditor;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class INBTTagEditor1_13 implements INBTTagEditor {

    @Override
    public ItemStack addNBTTag(ItemStack itemStack, String key, String value) {

        net.minecraft.server.v1_13_R2.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();

        tag.setString(key, value);

        stack.setTag(tag);

        return CraftItemStack.asCraftMirror(stack);

    }

    @Override
    public String getNBTTagValue(ItemStack itemStack, String key) {

        String ret = null;

        if(hasNBTTag(itemStack, key)){

            net.minecraft.server.v1_13_R2.ItemStack item = CraftItemStack.asNMSCopy(itemStack);

            ret = item.getTag().getString(key);

        }

        return ret;

    }

    @Override
    public boolean hasNBTTag(ItemStack itemStack, String key) {

        net.minecraft.server.v1_13_R2.ItemStack item = CraftItemStack.asNMSCopy(itemStack);

        if(item.getTag() != null) {

            return item.getTag().hasKey(key);

        }

        return false;

    }

}
