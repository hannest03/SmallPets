/*

Class created by SmallCode
18.07.2021 18:41

*/

import it.smallcode.smallpets.core.pets.recipe.Recipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeTest {

    @Test
    public void createRecipe(){
        ItemStack[] items = new ItemStack[9];
        items[0] = new ItemStack(Material.DIAMOND);
        Recipe recipe = new Recipe(items);
        assertEquals(items, recipe.getItems());
    }

}
