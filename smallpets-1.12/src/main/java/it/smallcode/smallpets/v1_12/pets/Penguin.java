package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 21:48

*/

import it.smallcode.smallpets.core.pets.recipe.Recipe;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;

public class Penguin extends it.smallcode.smallpets.v1_15.pets.Penguin {

    public Penguin() {
        super();

        ItemStack[] items = new ItemStack[9];
        items[0] = new ItemStack(Material.RAW_CHICKEN);
        items[1] = new ItemStack(Material.RAW_CHICKEN);
        items[2] = new ItemStack(Material.RAW_CHICKEN);
        items[3] = new ItemStack(Material.RAW_CHICKEN);
        items[4] = new ItemStack(Material.RAW_FISH, (short) 1);
        items[5] = new ItemStack(Material.RAW_CHICKEN);
        items[6] = new ItemStack(Material.RAW_CHICKEN);
        items[7] = new ItemStack(Material.RAW_CHICKEN);

        setRecipe(new Recipe(items));
    }

}
