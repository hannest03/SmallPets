package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
01.11.2020 20:12

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.abilities.FasterMiningSpeedAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Silverfish extends Pet {

    public Silverfish(String id, Player owner, Long xp, Boolean useProtocolLib) {

        super(id, owner, xp, useProtocolLib);

        super.setPetType(PetType.mining);

        super.abilities.add(new FasterMiningSpeedAbility(4, 1));

        super.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE5MWRhYjgzOTFhZjVmZGE1NGFjZDJjMGIxOGZiZDgxOWI4NjVlMWE4ZjFkNjIzODEzZmE3NjFlOTI0NTQwIn19fQ==";

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem(plugin);

        NamespacedKey key = new NamespacedKey(plugin, "pet_" + getID());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("C C", "SGS", "SSS");

        recipe.setIngredient('C', Material.COAL);
        recipe.setIngredient('G', Material.GOLDEN_PICKAXE);
        recipe.setIngredient('S', Material.STONE);

        Bukkit.addRecipe(recipe);

    }
}
