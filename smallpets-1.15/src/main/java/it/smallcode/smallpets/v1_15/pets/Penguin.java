package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
03.07.2020 17:51

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.SamplePet;
import it.smallcode.smallpets.v1_15.SkullCreator;
import it.smallcode.smallpets.v1_15.abilities.WaterSpeedAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Penguin extends SamplePet {

    public static final float MAXSWIMMINGSPEED = 0.25F;

    public Penguin(Player owner, Long xp, Boolean useProtocolLib, LanguageManager languageManager) {

        super(owner, xp, useProtocolLib, languageManager);

        super.setPetType(PetType.fishing);

        super.abilities.add(new WaterSpeedAbility(0.25, 1D));

    }

    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MwZDE2MTA3OTU2ZDc4NTNhMWJlMzE1NDljNDZhMmZmMjBiNDUxZDYzNjA3NTI4ZDVlMTk1YzQ0NTllMWZhMSJ9fX0=");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem(plugin);

        NamespacedKey key = new NamespacedKey(plugin, "pet_penguin");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("CCC", "CSC", "CCC");

        recipe.setIngredient('C', Material.CHICKEN);
        recipe.setIngredient('S', Material.SALMON);

        Bukkit.addRecipe(recipe);

    }

    @Override
    public String getAbility() {

        return "§7Makes you swim faster";

    }

    @Override
    public String getName() {

        return "penguin";

    }

}
