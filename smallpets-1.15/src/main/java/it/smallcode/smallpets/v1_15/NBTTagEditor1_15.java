package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
24.10.2020 13:27

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.utils.NBTTagEditor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class NBTTagEditor1_15 implements NBTTagEditor {

    @Override
    public ItemStack addNBTTag(ItemStack itemStack, String key, String value) {

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.getPersistentDataContainer().set(new NamespacedKey(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), key), PersistentDataType.STRING, value);

        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    @Override
    public String getNBTTagValue(ItemStack itemStack, String key) {

        String ret = null;

        ItemMeta itemMeta = itemStack.getItemMeta();

        if(hasNBTTag(itemStack, key))
            ret = itemMeta.getPersistentDataContainer().get(new NamespacedKey(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), key), PersistentDataType.STRING);

        return ret;

    }

    @Override
    public boolean hasNBTTag(ItemStack itemStack, String key) {

        ItemMeta itemMeta = itemStack.getItemMeta();

        return itemMeta.getPersistentDataContainer().has(new NamespacedKey(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), key), PersistentDataType.STRING);

    }
}
