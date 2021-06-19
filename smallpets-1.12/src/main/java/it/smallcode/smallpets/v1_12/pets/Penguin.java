package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 21:48

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.smallcode.smallpets.core.languages.LanguageManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class Penguin extends it.smallcode.smallpets.v1_15.pets.Penguin {
    public Penguin(String type, UUID uuid, Player owner, Long xp, Boolean useProtocolLib) {
        super(type, uuid, owner, xp, useProtocolLib);
    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem();

        NamespacedKey key = new NamespacedKey(plugin, "pet_penguin");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("CCC", "CSC", "CCC");

        recipe.setIngredient('C', Material.RAW_CHICKEN);
        recipe.setIngredient('S', Material.RAW_FISH, (short) 1);

        Bukkit.addRecipe(recipe);

    }

}
