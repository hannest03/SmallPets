package it.smallcode.smallpets.pets.v1_15;
/*

Class created by SmallCode
02.07.2020 16:09

*/

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCreator {

    public static ItemStack getSkull(String texture){

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();



        return head;

    }

}
