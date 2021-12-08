package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
01.11.2021 22:07

*/

import it.smallcode.smallpets.core.utils.ItemLoader;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemLoader1_12 implements ItemLoader {
    @Override
    public ItemStack load(Map<String, Object> data) {
        ItemStack itemStack = new ItemStack(1);
        return itemStack;
    }
}
