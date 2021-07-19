package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
03.11.2020 18:21

*/

import it.smallcode.smallpets.core.pets.recipe.Recipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Panda extends it.smallcode.smallpets.v1_15.pets.Panda {

    public Panda() {
        super();
        ItemStack[] items = new ItemStack[9];
        items[3] = new ItemStack(Material.WOOL, (short) 15);
        items[4] = new ItemStack(Material.LEAVES, (short) 3);
        items[5] = new ItemStack(Material.WOOL, (short) 0);

        setRecipe(new Recipe(items));
    }
}
