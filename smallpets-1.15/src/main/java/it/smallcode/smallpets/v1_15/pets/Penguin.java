package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
03.07.2020 17:51

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.abilities.HealthAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Penguin extends Pet {

    public Penguin(String type, Player owner, Long xp, Boolean useProtocolLib) {

        super(type, owner, xp, useProtocolLib);

        super.setPetType(PetType.fishing);

        super.setParticle(Particle.WATER_BUBBLE);

        super.abilities.add(new HealthAbility(10, 2));

        this.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MwZDE2MTA3OTU2ZDc4NTNhMWJlMzE1NDljNDZhMmZmMjBiNDUxZDYzNjA3NTI4ZDVlMTk1YzQ0NTllMWZhMSJ9fX0=";

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(plugin, "pet_" + getID());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("CCC", "CSC", "CCC");

        recipe.setIngredient('C', Material.CHICKEN);
        recipe.setIngredient('S', Material.SALMON);

        Bukkit.addRecipe(recipe);

    }

}
