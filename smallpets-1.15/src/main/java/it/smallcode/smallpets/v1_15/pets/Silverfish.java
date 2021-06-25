package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
01.11.2020 20:12

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.abilities.standard.FasterMiningSpeedAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.Date;
import java.util.UUID;

public class Silverfish extends Pet {

    public Silverfish(String id, Player owner, Long xp, Boolean useProtocolLib) {
        super(id, owner, xp, useProtocolLib);

        super.setPetType(PetType.mining);
        super.abilities.add(new FasterMiningSpeedAbility(4, 1));
    }

    @Override
    protected void updateTexture(){

        super.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE5MWRhYjgzOTFhZjVmZGE1NGFjZDJjMGIxOGZiZDgxOWI4NjVlMWE4ZjFkNjIzODEzZmE3NjFlOTI0NTQwIn19fQ==";

        if(new Date(System.currentTimeMillis()).getMonth() == 11)
            super.textureValue = "ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5Njc0OTc2NSwKICAicHJvZmlsZUlkIiA6ICJhYzczNzMyYmEzZTY0M2IxODE2ZDA2MDQ0M2U3ODhkOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUZXhXYXJkIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzhhNWIzMDdhNTRjZDQwYzIxNWQ2MGJlMzEzYzA1NDY2YWZkNjU5YWEwOTI1MTA5ZjRmZTRhY2EzZmIxZGExMmMiCiAgICB9CiAgfQp9";

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(plugin, "pet_" + getID());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("C C", "SGS", "SSS");

        recipe.setIngredient('C', Material.COAL);
        recipe.setIngredient('G', Material.GOLDEN_PICKAXE);
        recipe.setIngredient('S', Material.STONE);

        Bukkit.addRecipe(recipe);

    }
}
