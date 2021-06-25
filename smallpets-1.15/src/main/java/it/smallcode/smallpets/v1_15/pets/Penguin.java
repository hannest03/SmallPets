package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
03.07.2020 17:51

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.abilities.aureliumskills.AureliumSkillsHealthAbility;
import it.smallcode.smallpets.v1_15.abilities.standard.HealthAbility;
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

public class Penguin extends Pet {

    public Penguin(String id, Player owner, Long xp, Boolean useProtocolLib) {
        super(id, owner, xp, useProtocolLib);
        super.setPetType(PetType.fishing);

        super.setParticle(Particle.WATER_BUBBLE);

        super.abilities.add(new HealthAbility(10, 2));
    }

    @Override
    protected void updateTexture(){

        this.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MwZDE2MTA3OTU2ZDc4NTNhMWJlMzE1NDljNDZhMmZmMjBiNDUxZDYzNjA3NTI4ZDVlMTk1YzQ0NTllMWZhMSJ9fX0=";

        if(new Date(System.currentTimeMillis()).getMonth() == 11)
            super.textureValue = "ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5Njg5MTQ2NSwKICAicHJvZmlsZUlkIiA6ICI5MWYwNGZlOTBmMzY0M2I1OGYyMGUzMzc1Zjg2ZDM5ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdG9ybVN0b3JteSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yZmZlNjdkMzhlOGNiZDM4NjJlYzJhY2VkNjc5YWE1ODcxMmRmMzdmNmI5MDI5ZTFlNjM2ZmQ0NDE4ZWVmYTE2IgogICAgfQogIH0KfQ==";

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(plugin, "pet_" + getID());

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("CCC", "CSC", "CCC");

        recipe.setIngredient('C', Material.CHICKEN);
        recipe.setIngredient('S', Material.SALMON);

        Bukkit.addRecipe(recipe);

    }

}
