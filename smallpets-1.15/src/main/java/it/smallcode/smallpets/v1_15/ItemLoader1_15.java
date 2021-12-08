package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
01.11.2021 22:06

*/

import it.smallcode.smallpets.core.utils.ColorUtils;
import it.smallcode.smallpets.core.utils.ItemLoader;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class ItemLoader1_15 implements ItemLoader {

    @Override
    public ItemStack load(Map<String, Object> data) {
        if(!data.containsKey("material") || Material.valueOf(((String) data.get("material")).toUpperCase()) == null)
            return null;

        ItemStack itemStack = new ItemStack(Material.valueOf(((String) data.get("material")).toUpperCase()));
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(data.containsKey("name")){
            String name = (String) data.get("name");
            itemMeta.setDisplayName(ColorUtils.translateColors(name));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
