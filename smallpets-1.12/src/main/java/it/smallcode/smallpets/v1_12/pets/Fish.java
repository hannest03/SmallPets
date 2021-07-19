package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
03.11.2020 18:24

*/

import it.smallcode.smallpets.core.pets.recipe.Recipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Fish extends it.smallcode.smallpets.v1_15.pets.Fish {

    public Fish() {
        super();
        ItemStack[] items = new ItemStack[9];
        items[1] = new ItemStack(Material.LEASH);
        items[4] = new ItemStack(Material.RAW_FISH);
        items[7] = new ItemStack(Material.WATER_BUCKET);

        setRecipe(new Recipe(items));
    }
}
