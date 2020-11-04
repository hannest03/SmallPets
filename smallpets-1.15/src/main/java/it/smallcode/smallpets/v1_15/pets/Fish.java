package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
01.11.2020 20:13

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.abilities.HealWhileInWaterAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Fish extends Pet {


    public Fish(String id, Player owner, Long xp, Boolean useProtocolLib) {

        super(id, owner, xp, useProtocolLib);

        super.setPetType(PetType.fishing);

        super.setParticle(Particle.WATER_BUBBLE);

        super.abilities.add(new HealWhileInWaterAbility(3, 1));

        super.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBjZDcxZmJiYmJiNjZjN2JhZjc4ODFmNDE1YzY0ZmE4NGY2NTA0OTU4YTU3Y2NkYjg1ODkyNTI2NDdlYSJ9fX0=";

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(plugin, "pet_" + getID());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" L ", " S ", " B ");

        recipe.setIngredient('L', Material.LEAD);
        recipe.setIngredient('S', Material.SALMON);
        recipe.setIngredient('B', Material.WATER_BUCKET);

        Bukkit.addRecipe(recipe);

    }
}
