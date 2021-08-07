package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
03.07.2020 17:51

*/

import it.smallcode.smallpets.core.abilities.abilities.HealthAbility;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.recipe.Recipe;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import java.util.Date;

public class Penguin extends Pet {

    public Penguin() {
        super();
        super.setPetType(PetType.FISHING);

        super.setParticle(Particle.WATER_BUBBLE);

        HealthAbility healthAbility = new HealthAbility();
        healthAbility.setMaxStatBoost(10);
        healthAbility.setMinStatBoost(2);
        super.getAbilities().add(healthAbility);

        ItemStack[] items = new ItemStack[9];
        items[0] = new ItemStack(Material.CHICKEN);
        items[1] = new ItemStack(Material.CHICKEN);
        items[2] = new ItemStack(Material.CHICKEN);
        items[3] = new ItemStack(Material.CHICKEN);
        items[5] = new ItemStack(Material.CHICKEN);
        items[6] = new ItemStack(Material.CHICKEN);
        items[7] = new ItemStack(Material.CHICKEN);
        items[8] = new ItemStack(Material.CHICKEN);

        items[4] = new ItemStack(Material.SALMON);

        setRecipe(new Recipe(items));
    }

    @Override
    protected void updateTexture(){

        setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MwZDE2MTA3OTU2ZDc4NTNhMWJlMzE1NDljNDZhMmZmMjBiNDUxZDYzNjA3NTI4ZDVlMTk1YzQ0NTllMWZhMSJ9fX0=");

        if(new Date(System.currentTimeMillis()).getMonth() == 11)
            setTextureValue("ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5Njg5MTQ2NSwKICAicHJvZmlsZUlkIiA6ICI5MWYwNGZlOTBmMzY0M2I1OGYyMGUzMzc1Zjg2ZDM5ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdG9ybVN0b3JteSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yZmZlNjdkMzhlOGNiZDM4NjJlYzJhY2VkNjc5YWE1ODcxMmRmMzdmNmI5MDI5ZTFlNjM2ZmQ0NDE4ZWVmYTE2IgogICAgfQogIH0KfQ==");

    }
}
