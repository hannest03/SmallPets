package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
04.07.2020 12:58

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.recipe.Recipe;
import it.smallcode.smallpets.v1_15.abilities.standard.SpeedBoostInBiomeAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class Monkey extends Pet {

    public Monkey() {
        super();
        super.setPetType(PetType.foraging);
        super.getAbilities().add(new SpeedBoostInBiomeAbility(new LinkedList<>(Arrays.asList(Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.BAMBOO_JUNGLE, Biome.BAMBOO_JUNGLE_HILLS, Biome.MODIFIED_JUNGLE)), 0.01D, 0.05D));

        ItemStack[] items = new ItemStack[9];
        items[1] = new ItemStack(Material.LEATHER);
        items[4] = new ItemStack(Material.LEATHER);
        items[7] = new ItemStack(Material.LEATHER);

        items[3] = new ItemStack(Material.COCOA_BEANS);
        items[5] = new ItemStack(Material.COCOA_BEANS);

        setRecipe(new Recipe(items));

    }

    @Override
    protected void updateTexture(){

        setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNkNGExNTYwM2Y5NTFkZTJlMmFiODBlZWQxNmJiYjVhNTgyM2JmNGFjYjhjNDYzMzQyNWQ1NDIxMGNmMGFkNSJ9fX0=");

        if(new Date(System.currentTimeMillis()).getMonth() == 11)
            setTextureValue("ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5NzE4MzE5NiwKICAicHJvZmlsZUlkIiA6ICI5MThhMDI5NTU5ZGQ0Y2U2YjE2ZjdhNWQ1M2VmYjQxMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCZWV2ZWxvcGVyIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQ5NDdiODU5MmI1NDZmNTY5OWU1YjE0YmI3MGM3YmRjZWZiZDMyNjNhYTRmZjFhNzk0NWNlZmMxNWE2NmQ1ZjQiCiAgICB9CiAgfQp9");

    }

}
