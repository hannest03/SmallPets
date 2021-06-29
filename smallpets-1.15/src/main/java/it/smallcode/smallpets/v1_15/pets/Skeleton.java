package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
01.11.2020 19:05

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.abilities.standard.DontConsumeArrowAbility;
import it.smallcode.smallpets.v1_15.abilities.standard.UnbreakableBowAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.Date;
import java.util.UUID;

public class Skeleton extends Pet {

    public Skeleton() {
        super();

        super.setPetType(PetType.combat);
        super.setParticle(Particle.CLOUD);

        super.getAbilities().add(new DontConsumeArrowAbility(10, 1));
        super.getAbilities().add(new UnbreakableBowAbility());
    }


    @Override
    protected void updateTexture() {

        setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxMjY4ZTljNDkyZGExZjBkODgyNzFjYjQ5MmE0YjMwMjM5NWY1MTVhN2JiZjc3ZjRhMjBiOTVmYzAyZWIyIn19fQ==");

        if (new Date(System.currentTimeMillis()).getMonth() == 11)
            setTextureValue("ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5NjU4MDM0MSwKICAicHJvZmlsZUlkIiA6ICJiYzRlZGZiNWYzNmM0OGE3YWM5ZjFhMzlkYzIzZjRmOCIsCiAgInByb2ZpbGVOYW1lIiA6ICI4YWNhNjgwYjIyNDYxMzQwIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzNjMzJhMWZiMmVhNWIzOWUzZDg3ZDNkYmU3ZDg0ODk4NTNiNjA3OWE4M2E5YzcwNjMxNjk1ZDFlNTdmMzk4ZDgiCiAgICB9CiAgfQp9");

    }

    @Override
    public void registerRecipe() {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), "pet_" + getId());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" B ", "BWB", " B ");

        recipe.setIngredient('B', Material.BONE);
        recipe.setIngredient('W', Material.BOW);

        Bukkit.addRecipe(recipe);

    }

}
