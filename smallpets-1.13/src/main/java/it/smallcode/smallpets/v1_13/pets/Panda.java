package it.smallcode.smallpets.v1_13.pets;
/*

Class created by SmallCode
03.01.2021 11:36

*/

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class Panda extends it.smallcode.smallpets.v1_15.pets.Panda {

    public Panda(String id, Player owner, Long xp, Boolean useProtocolLib) {
        super(id, owner, xp, useProtocolLib);
    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(plugin, "pet_" + getID());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("   ", "SBW", "   ");

        recipe.setIngredient('S', Material.BLACK_WOOL);
        recipe.setIngredient('B', Material.OAK_LEAVES);
        recipe.setIngredient('W', Material.WHITE_WOOL);

        Bukkit.addRecipe(recipe);

    }
}
