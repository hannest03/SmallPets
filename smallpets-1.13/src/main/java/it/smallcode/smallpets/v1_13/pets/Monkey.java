package it.smallcode.smallpets.v1_13.pets;
/*

Class created by SmallCode
03.01.2021 11:39

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.v1_15.abilities.standard.SpeedBoostInBiomeAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class Monkey extends it.smallcode.smallpets.v1_15.pets.Monkey {

    public Monkey() {
        super();
        super.getAbilities().clear();
        super.getAbilities().add(new SpeedBoostInBiomeAbility(new LinkedList<>(Arrays.asList(Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.MODIFIED_JUNGLE)), 0.2D));
    }

    @Override
    public void registerRecipe() {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), "pet_" + getId());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" L ", "CLC", " L ");

        recipe.setIngredient('C', Material.COCOA_BEANS);
        recipe.setIngredient('L', Material.LEATHER);

        Bukkit.addRecipe(recipe);

    }
}
