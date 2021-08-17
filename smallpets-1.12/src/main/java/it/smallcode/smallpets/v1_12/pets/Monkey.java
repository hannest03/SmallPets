package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 21:49

*/

import it.smallcode.smallpets.core.pets.recipe.Recipe;
import it.smallcode.smallpets.v1_15.abilities.standard.SpeedBoostInBiomeAbility;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.LinkedList;

public class Monkey extends it.smallcode.smallpets.v1_15.pets.Monkey {

    public Monkey() {
        super();
        super.getAbilities().clear();

        SpeedBoostInBiomeAbility speedBoostInBiomeAbility = new SpeedBoostInBiomeAbility();
        speedBoostInBiomeAbility.setBiomes(new LinkedList<>(Arrays.asList(Biome.JUNGLE, Biome.JUNGLE_HILLS)));
        speedBoostInBiomeAbility.setMinStatBoost(0.01D);
        speedBoostInBiomeAbility.setMaxStatBoost(0.05D);
        super.getAbilities().add(speedBoostInBiomeAbility);

        ItemStack[] items = new ItemStack[9];
        items[1] = new ItemStack(Material.LEATHER);
        items[4] = new ItemStack(Material.LEATHER);
        items[7] = new ItemStack(Material.LEATHER);

        items[3] = new ItemStack(Material.getMaterial(351), (short) 3);
        items[5] = new ItemStack(Material.getMaterial(351), (short) 3);

        setRecipe(new Recipe(items));
    }

}
