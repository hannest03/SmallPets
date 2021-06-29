package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 21:48

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Penguin extends it.smallcode.smallpets.v1_15.pets.Penguin {

    public Penguin() {
        super();
    }

    @Override
    public void registerRecipe() {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), "pet_" + getId());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("CCC", "CSC", "CCC");

        recipe.setIngredient('C', Material.RAW_CHICKEN);
        recipe.setIngredient('S', Material.RAW_FISH, (short) 1);

        Bukkit.addRecipe(recipe);

    }

}
