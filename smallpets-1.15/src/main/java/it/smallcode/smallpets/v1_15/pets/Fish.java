package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
01.11.2020 20:13

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.recipe.Recipe;
import it.smallcode.smallpets.v1_15.abilities.standard.HealWhileInWaterAbility;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import java.util.Date;

public class Fish extends Pet {

    public Fish() {
        super();
        super.setPetType(PetType.FISHING);
        super.setParticle(Particle.WATER_BUBBLE);
        super.getAbilities().add(new HealWhileInWaterAbility(3, 1));

        ItemStack[] items = new ItemStack[9];
        items[1] = new ItemStack(Material.LEAD);
        items[4] = new ItemStack(Material.SALMON);
        items[7] = new ItemStack(Material.WATER_BUCKET);

        setRecipe(new Recipe(items));

    }

    @Override
    protected void updateTexture(){

        setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBjZDcxZmJiYmJiNjZjN2JhZjc4ODFmNDE1YzY0ZmE4NGY2NTA0OTU4YTU3Y2NkYjg1ODkyNTI2NDdlYSJ9fX0=");

        if(new Date(System.currentTimeMillis()).getMonth() == 11)
            setTextureValue("ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5NzM2ODY2OSwKICAicHJvZmlsZUlkIiA6ICJjNTBhZmE4YWJlYjk0ZTQ1OTRiZjFiNDI1YTk4MGYwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJUd29FQmFlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzk5MjVmYjMzNzg2YWE2YmVlNjdhMTFlZTk2ZmFhY2IxNWE0MjE5YzYwOWUyMmFjZTM3MmJmYTMyNDkxNTNlNDAiCiAgICB9CiAgfQp9");


    }
}
