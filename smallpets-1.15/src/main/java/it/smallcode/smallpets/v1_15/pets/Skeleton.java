package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
01.11.2020 19:05

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.abilities.DontConsumeArrowAbility;
import it.smallcode.smallpets.v1_15.abilities.UnbreakableBowAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Skeleton extends Pet {

    public Skeleton(String id, Player owner, Long xp, Boolean useProtocolLib) {

        super(id, owner, xp, useProtocolLib);

        super.setPetType(PetType.combat);
        super.setParticle(Particle.CLOUD);

        super.abilities.add(new DontConsumeArrowAbility(10, 1));
        super.abilities.add(new UnbreakableBowAbility());

        super.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxMjY4ZTljNDkyZGExZjBkODgyNzFjYjQ5MmE0YjMwMjM5NWY1MTVhN2JiZjc3ZjRhMjBiOTVmYzAyZWIyIn19fQ==";

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem(plugin);

        NamespacedKey key = new NamespacedKey(plugin, "pet_" + getID());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" B ", "BWB", " B ");

        recipe.setIngredient('B', Material.BONE);
        recipe.setIngredient('W', Material.BOW);

        Bukkit.addRecipe(recipe);

    }

}
