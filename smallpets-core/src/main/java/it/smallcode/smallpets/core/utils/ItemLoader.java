package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
01.11.2021 21:56

*/

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface ItemLoader {
    ItemStack load(Map<String, Object> data);
}
