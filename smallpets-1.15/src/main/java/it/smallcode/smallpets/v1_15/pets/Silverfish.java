package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
01.11.2020 20:12

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.recipe.Recipe;
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

    public Silverfish() {
        super();
        super.setPetType(PetType.mining);
        super.getAbilities().add(new FasterMiningSpeedAbility(4, 1));

        ItemStack[] items = new ItemStack[9];
        items[0] = new ItemStack(Material.COAL);
        items[2] = new ItemStack(Material.COAL);

        items[3] = new ItemStack(Material.STONE);
        items[4] = new ItemStack(Material.GOLDEN_PICKAXE);
        items[5] = new ItemStack(Material.STONE);

        items[6] = new ItemStack(Material.STONE);
        items[7] = new ItemStack(Material.STONE);
        items[8] = new ItemStack(Material.STONE);

        setRecipe(new Recipe(items));
    }

    @Override
    protected void updateTexture(){

        setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE5MWRhYjgzOTFhZjVmZGE1NGFjZDJjMGIxOGZiZDgxOWI4NjVlMWE4ZjFkNjIzODEzZmE3NjFlOTI0NTQwIn19fQ==");

        if(new Date(System.currentTimeMillis()).getMonth() == 11)
            setTextureValue("ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5Njc0OTc2NSwKICAicHJvZmlsZUlkIiA6ICJhYzczNzMyYmEzZTY0M2IxODE2ZDA2MDQ0M2U3ODhkOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUZXhXYXJkIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzhhNWIzMDdhNTRjZDQwYzIxNWQ2MGJlMzEzYzA1NDY2YWZkNjU5YWEwOTI1MTA5ZjRmZTRhY2EzZmIxZGExMmMiCiAgICB9CiAgfQp9");

    }
}
