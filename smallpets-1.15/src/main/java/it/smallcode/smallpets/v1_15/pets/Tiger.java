package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
02.07.2020 14:57

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.recipe.Recipe;
import it.smallcode.smallpets.v1_15.abilities.standard.DamageAbility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Date;

public class Tiger extends Pet {

    public Tiger() {
        super();

        super.setPetType(PetType.COMBAT);
        super.getAbilities().add(new DamageAbility(20));

        ItemStack[] items = new ItemStack[9];
        items[1] = new ItemStack(Material.MUTTON);

        items[3] = new ItemStack(Material.CHICKEN);
        items[4] = new ItemStack(Material.BEEF);
        items[5] = new ItemStack(Material.RABBIT);

        items[7] = new ItemStack(Material.PORKCHOP);

        setRecipe(new Recipe(items));
    }

    @Override
    protected void updateTexture(){

        setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM0MjYzODc0NDkyMmI1ZmNmNjJjZDliZjI3ZWVhYjkxYjJlNzJkNmM3MGU4NmNjNWFhMzg4Mzk5M2U5ZDg0In19fQ==");

        if(new Date(System.currentTimeMillis()).getMonth() == 11)
            setTextureValue("ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5NTQ4NjI3NSwKICAicHJvZmlsZUlkIiA6ICJmMzA1ZjA5NDI0NTg0ZjU4YmEyYjY0ZjAyZDcyNDYyYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJqcm9ja2EzMyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mYjc2ZjE2ZjZmNzBhNmQ4YWNkY2JjYzc4N2IwNWNiNGM1ZDUzMjE3M2FhZTNhNTc4OThjYTAzYTM0MDRmNDEiCiAgICB9CiAgfQp9");

    }
}
