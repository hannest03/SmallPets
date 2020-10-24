package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
24.10.2020 13:23

*/

import org.bukkit.inventory.ItemStack;

public interface INBTTagEditor {

    ItemStack addNBTTag(ItemStack itemStack, String key, String value);

    String getNBTTagValue(ItemStack itemStack, String key);

    boolean hasNBTTag(ItemStack itemStack, String key);

}
