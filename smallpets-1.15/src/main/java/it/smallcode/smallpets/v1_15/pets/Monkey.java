package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
04.07.2020 12:58

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.v1_15.SamplePet;
import it.smallcode.smallpets.v1_15.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Monkey extends SamplePet {

    public static double MAXJUMPHEIGHT = 0.05;

    public Monkey(Player owner, Long xp, Boolean useProtocolLib, LanguageManager languageManager) {
        super(owner, xp, useProtocolLib, languageManager);
    }

    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNkNGExNTYwM2Y5NTFkZTJlMmFiODBlZWQxNmJiYjVhNTgyM2JmNGFjYjhjNDYzMzQyNWQ1NDIxMGNmMGFkNSJ9fX0=");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getLanguageManager().getLanguage().getStringFormatted("pet." + getName()));

        skull.setItemMeta(skullMeta);

        return skull;

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem(plugin);

        NamespacedKey key = new NamespacedKey(plugin, "pet_monkey");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" L ", "CLC", " L ");

        recipe.setIngredient('C', Material.COCOA_BEANS);
        recipe.setIngredient('L', Material.LEATHER);

        Bukkit.addRecipe(recipe);

    }

    /**
     *
     * Returns the item to unlock the tiger
     *
     * @param plugin - the plugin
     * @return the item to unlock the tiger
     */
    @Override
    public ItemStack getUnlockItem(Plugin plugin){

        ItemStack item = getItem();

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName("§6" + getLanguageManager().getLanguage().getStringFormatted("pet." + getName()));

        ArrayList<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§6RIGHT CLICK TO UNLOCK");

        itemMeta.setLore(lore);

        itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "pet"), PersistentDataType.STRING, getName());

        item.setItemMeta(itemMeta);

        return item;

    }

    @Override
    public String getAbility() {

        return "§c+" + (int) (((MAXJUMPHEIGHT / 100D) * getLevel() * 100D) * 100D) / 100D  + "%§7 jump height";

    }

    @Override
    public String getName() {

        return "monkey";

    }

}
