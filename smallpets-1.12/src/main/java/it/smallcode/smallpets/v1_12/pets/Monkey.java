package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 21:49

*/

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

public class Monkey extends Pet {

    public Monkey(String id, Player owner, Long xp, Boolean useProtocolLib) {
        super(id, owner, xp, useProtocolLib);
        super.setPetType(PetType.foraging);

        this.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNkNGExNTYwM2Y5NTFkZTJlMmFiODBlZWQxNmJiYjVhNTgyM2JmNGFjYjhjNDYzMzQyNWQ1NDIxMGNmMGFkNSJ9fX0=";

        super.abilities.add(new SpeedBoostInBiomeAbility(new LinkedList<>(Arrays.asList(Biome.JUNGLE, Biome.JUNGLE_HILLS)), 0.01D, 0.05D));
    }

    @Override
    protected void updateTexture(){

        this.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNkNGExNTYwM2Y5NTFkZTJlMmFiODBlZWQxNmJiYjVhNTgyM2JmNGFjYjhjNDYzMzQyNWQ1NDIxMGNmMGFkNSJ9fX0=";

        if(new Date(System.currentTimeMillis()).getMonth() == 11)
            super.textureValue = "ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5NzE4MzE5NiwKICAicHJvZmlsZUlkIiA6ICI5MThhMDI5NTU5ZGQ0Y2U2YjE2ZjdhNWQ1M2VmYjQxMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJCZWV2ZWxvcGVyIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQ5NDdiODU5MmI1NDZmNTY5OWU1YjE0YmI3MGM3YmRjZWZiZDMyNjNhYTRmZjFhNzk0NWNlZmMxNWE2NmQ1ZjQiCiAgICB9CiAgfQp9";

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(plugin, "pet_monkey");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" L ", "CLC", " L ");

        recipe.setIngredient('C', Material.getMaterial(351), (short) 3);
        recipe.setIngredient('L', Material.LEATHER);

        Bukkit.addRecipe(recipe);

    }

}
