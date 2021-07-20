package it.smallcode.smallpets.v1_13.pets;
/*

Class created by SmallCode
03.01.2021 11:36

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.recipe.Recipe;
import it.smallcode.smallpets.v1_15.abilities.standard.DamageAbility;
import it.smallcode.smallpets.v1_15.abilities.standard.HealthAbility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Date;

public class Panda extends Pet {

    public Panda() {
        super();

        super.setPetType(PetType.FARMING);

        super.getAbilities().add(new HealthAbility(4));

        super.getAbilities().add(new DamageAbility(5, 1));

        ItemStack[] items = new ItemStack[9];
        items[3] = new ItemStack(Material.BLACK_WOOL);
        items[4] = new ItemStack(Material.OAK_LEAVES);
        items[5] = new ItemStack(Material.WHITE_WOOL);

        setRecipe(new Recipe(items));
        Arrays.stream(getRecipe().getItems()).forEach(System.out::println);
    }

    @Override
    protected void updateTexture(){

        setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGNhMDk2ZWVhNTA2MzAxYmVhNmQ0YjE3ZWUxNjA1NjI1YTZmNTA4MmM3MWY3NGE2MzljYzk0MDQzOWY0NzE2NiJ9fX0=");

        if(new Date(System.currentTimeMillis()).getMonth() == 11)
            setTextureValue("ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5NzAyMTM2MSwKICAicHJvZmlsZUlkIiA6ICIxNzhmMTJkYWMzNTQ0ZjRhYjExNzkyZDc1MDkzY2JmYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaWxlbnRkZXRydWN0aW9uIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc0ZGMwN2M4ZjEwYzQ4MmI3NzA5NTQxN2M5ODRhOTg0NmI3MjIzMmZjNGQzMDBhZDM2OWZmZmMzZTc1MjE0OWIiCiAgICB9CiAgfQp9");

    }
}
