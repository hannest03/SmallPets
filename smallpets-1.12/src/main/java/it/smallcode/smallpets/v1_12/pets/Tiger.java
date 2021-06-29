package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 19:01

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Tiger extends it.smallcode.smallpets.v1_15.pets.Tiger {

    public Tiger() {
        super();
    }

    @Override
    public void registerRecipe() {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), "pet_" + getId());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" M ", "CBR", " P ");

        recipe.setIngredient('M', Material.MUTTON);
        recipe.setIngredient('C', Material.RAW_CHICKEN);
        recipe.setIngredient('B', Material.RAW_BEEF);
        recipe.setIngredient('R', Material.RABBIT);
        recipe.setIngredient('P', Material.PORK);

        Bukkit.addRecipe(recipe);

    }

}
