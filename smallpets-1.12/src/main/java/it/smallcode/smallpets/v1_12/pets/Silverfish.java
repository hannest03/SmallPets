package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
03.11.2020 18:24

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class Silverfish extends it.smallcode.smallpets.v1_15.pets.Silverfish {

    public Silverfish() {
        super();
    }

    @Override
    public void registerRecipe() {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), "pet_" + getId());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("C C", "SGS", "SSS");

        recipe.setIngredient('C', Material.COAL);
        recipe.setIngredient('G', Material.GOLD_PICKAXE);
        recipe.setIngredient('S', Material.STONE);

        Bukkit.addRecipe(recipe);

    }
}
