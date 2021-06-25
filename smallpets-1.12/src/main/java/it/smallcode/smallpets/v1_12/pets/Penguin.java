package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 21:48

*/

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Penguin extends it.smallcode.smallpets.v1_15.pets.Penguin {
    public Penguin(String type, Player owner, Long xp, Boolean useProtocolLib) {
        super(type, owner, xp, useProtocolLib);
    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(plugin, "pet_penguin");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("CCC", "CSC", "CCC");

        recipe.setIngredient('C', Material.RAW_CHICKEN);
        recipe.setIngredient('S', Material.RAW_FISH, (short) 1);

        Bukkit.addRecipe(recipe);

    }

}
