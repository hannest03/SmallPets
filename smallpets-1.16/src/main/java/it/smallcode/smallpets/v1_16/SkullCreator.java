package it.smallcode.smallpets.v1_16;
/*

Class created by SmallCode
05.07.2020 20:32

*/

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SkullCreator {

    /**
     *
     * Returns a player head with custom texture
     *
     * @param texture - the new texture
     * @return the player head with custom texture
     */
    public static ItemStack getSkull(String id, String texture){

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        Bukkit.getUnsafe().modifyItemStack(head, "{SkullOwner:{Id:" + id + ",Properties:{textures:[{Value:\"" + texture + "\"}]}}}");

        return head;

    }

}
