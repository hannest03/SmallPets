package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
02.07.2020 14:57

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.SamplePet;
import it.smallcode.smallpets.v1_15.SkullCreator;
import it.smallcode.smallpets.v1_15.abilities.DamageAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Tiger extends SamplePet {

    public static final float MAXDAMAGEMULTIPLIER = 0.15F;

    /**
     * Creates a pet
     *
     * @param owner           - the pet owner
     * @param xp              - the xp
     * @param useProtocolLib
     * @param languageManager
     */
    public Tiger(Player owner, Long xp, Boolean useProtocolLib, LanguageManager languageManager) {
        super(owner, xp, useProtocolLib, languageManager);

        super.setPetType(PetType.combat);

        super.abilities.add(new DamageAbility());

    }
    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM0MjYzODc0NDkyMmI1ZmNmNjJjZDliZjI3ZWVhYjkxYjJlNzJkNmM3MGU4NmNjNWFhMzg4Mzk5M2U5ZDg0In19fQ==");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem(plugin);

        NamespacedKey key = new NamespacedKey(plugin, "pet_tiger");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" M ", "CBR", " P ");

        recipe.setIngredient('M', Material.MUTTON);
        recipe.setIngredient('C', Material.CHICKEN);
        recipe.setIngredient('B', Material.BEEF);
        recipe.setIngredient('R', Material.RABBIT);
        recipe.setIngredient('P', Material.PORKCHOP);

        Bukkit.addRecipe(recipe);

    }

    @Override
    public String getAbility() {

        return "§c+" + (int) ((MAXDAMAGEMULTIPLIER / 100D * getLevel() * 100) * 100) / 100D + "%§7 more damage";

    }

    @Override
    public String getName() {

        return "tiger";

    }

}
