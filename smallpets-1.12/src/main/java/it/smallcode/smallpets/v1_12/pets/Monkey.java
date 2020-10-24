package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
09.07.2020 21:49

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.pets.PetType;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class Monkey extends it.smallcode.smallpets.v1_15.pets.Monkey {

    public Monkey(String type, Player owner, Long xp, Boolean useProtocolLib) {

        super(type, owner, xp, useProtocolLib);

        super.setPetType(PetType.foraging);

    }

    @Override
    public void registerRecipe(Plugin plugin) {

        ItemStack item = getUnlockItem(plugin);

        NamespacedKey key = new NamespacedKey(plugin, "pet_monkey");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape(" L ", "CLC", " L ");

        recipe.setIngredient('C', Material.getMaterial(351), (short) 3);
        recipe.setIngredient('L', Material.LEATHER);

        Bukkit.addRecipe(recipe);

    }

}
