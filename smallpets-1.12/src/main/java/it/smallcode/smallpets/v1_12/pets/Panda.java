package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
03.11.2020 18:21

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

        recipe.setIngredient('S', Material.WOOL, (short) 15);
        recipe.setIngredient('B', Material.LEAVES, (short) 3);
        recipe.setIngredient('W', Material.WOOL, (short) 0);

        Bukkit.addRecipe(recipe);

    }
}
