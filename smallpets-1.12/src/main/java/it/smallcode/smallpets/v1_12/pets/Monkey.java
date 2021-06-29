package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 21:49

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.abilities.standard.SpeedBoostInBiomeAbility;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class Monkey extends it.smallcode.smallpets.v1_15.pets.Monkey {

    public Monkey() {
        super();
        super.getAbilities().clear();
        super.getAbilities().add(new SpeedBoostInBiomeAbility(new LinkedList<>(Arrays.asList(Biome.JUNGLE, Biome.JUNGLE_HILLS)), 0.2D));
    }

    @Override
    public void registerRecipe() {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), "pet_" + getId());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" L ", "CLC", " L ");

        recipe.setIngredient('C', Material.getMaterial(351), (short) 3);
        recipe.setIngredient('L', Material.LEATHER);

        Bukkit.addRecipe(recipe);

    }

}
