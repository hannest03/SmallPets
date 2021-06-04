package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
24.10.2020 13:35

*/

import it.smallcode.smallpets.core.utils.INBTTagEditor;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class INBTTagEditor1_12 implements INBTTagEditor {


    @Override
    public ItemStack addNBTTag(ItemStack itemStack, String key, String value) {

        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tag = stack.getTag() != null ? stack.getTag() : new NBTTagCompound();

        tag.setString(key, value);

        stack.setTag(tag);

        return CraftItemStack.asCraftMirror(stack);

    }

    @Override
    public String getNBTTagValue(ItemStack itemStack, String key) {

        String ret = null;

        if(hasNBTTag(itemStack, key)){

            net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);

            ret = item.getTag().getString(key);

        }

        return ret;

    }

    @Override
    public boolean hasNBTTag(ItemStack itemStack, String key) {

        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);

        if(item.getTag() != null) {

            return item.getTag().hasKey(key);

        }

        return false;

    }

}
