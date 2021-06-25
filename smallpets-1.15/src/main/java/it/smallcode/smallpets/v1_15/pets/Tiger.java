package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
02.07.2020 14:57

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.abilities.standard.DamageAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.Date;
import java.util.UUID;

public class Tiger extends Pet {

    public Tiger(String id, Player owner, Long xp, Boolean useProtocolLib) {
        super(id, owner, xp, useProtocolLib);

        super.setPetType(PetType.combat);
        super.abilities.add(new DamageAbility(20));
    }

    @Override
    protected void updateTexture(){

        super.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM0MjYzODc0NDkyMmI1ZmNmNjJjZDliZjI3ZWVhYjkxYjJlNzJkNmM3MGU4NmNjNWFhMzg4Mzk5M2U5ZDg0In19fQ==";

        if(new Date(System.currentTimeMillis()).getMonth() == 11)
            super.textureValue = "ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5NTQ4NjI3NSwKICAicHJvZmlsZUlkIiA6ICJmMzA1ZjA5NDI0NTg0ZjU4YmEyYjY0ZjAyZDcyNDYyYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJqcm9ja2EzMyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mYjc2ZjE2ZjZmNzBhNmQ4YWNkY2JjYzc4N2IwNWNiNGM1ZDUzMjE3M2FhZTNhNTc4OThjYTAzYTM0MDRmNDEiCiAgICB9CiAgfQp9";

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(plugin, "pet_" + getID());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" M ", "CBR", " P ");

        recipe.setIngredient('M', Material.MUTTON);
        recipe.setIngredient('C', Material.CHICKEN);
        recipe.setIngredient('B', Material.BEEF);
        recipe.setIngredient('R', Material.RABBIT);
        recipe.setIngredient('P', Material.PORKCHOP);

        Bukkit.addRecipe(recipe);

    }

}
