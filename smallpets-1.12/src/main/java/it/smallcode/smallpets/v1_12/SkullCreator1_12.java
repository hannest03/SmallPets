package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
24.10.2020 20:43

*/

import it.smallcode.smallpets.core.utils.SkullCreator;
import org.bukkit.inventory.ItemStack;

public class SkullCreator1_12 extends SkullCreator {


    @Override
    public ItemStack createHeadItem(String texture) {

        ItemStack head = new ItemStack(397, 1, (short) 3);

        setHeadTexture(head, texture);

        return head;

    }
}
