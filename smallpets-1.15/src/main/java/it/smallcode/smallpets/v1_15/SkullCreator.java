package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
02.07.2020 16:09

*/

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SkullCreator {

    /**
     *
     * Returns a player head with custom texture
     *
     * @param texture - the new texture
     * @return the player head with custom texture
     */
    public static ItemStack getSkull(String texture){

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        UUID hashAsId = new UUID(texture.hashCode(), texture.hashCode());

        Bukkit.getUnsafe().modifyItemStack(head, "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + texture + "\"}]}}}");

        return head;

    }

}
