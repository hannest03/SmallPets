package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 19:01

*/

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Tiger extends it.smallcode.smallpets.v1_15.pets.Tiger {

    /**
     * Creates a pet
     *
     * @param owner           - the pet owner
     * @param xp              - the xp
     * @param useProtocolLib
     * @param languageManager
     */
    public Tiger(String type, Player owner, Long xp, Boolean useProtocolLib) {
        super(type, owner, xp, useProtocolLib);
    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(plugin, "pet_tiger");

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
