package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
24.10.2020 20:41

*/

import it.smallcode.smallpets.core.utils.SkullCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SkullCreator1_15 extends SkullCreator {

    @Override
    public ItemStack createHeadItem(String texture) {

        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);

        setHeadTexture(head, texture);

        return head;

    }

}
