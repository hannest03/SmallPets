package it.smallcode.smallpets.v1_13;
/*

Class created by SmallCode
01.08.2020 21:48

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.text.CenteredText;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SamplePet extends it.smallcode.smallpets.v1_15.SamplePet {


    /**
     * Creates a pet
     *
     * @param owner           - the pet owner
     * @param xp              - the xp
     * @param useProtocolLib
     * @param languageManager
     */
    public SamplePet(Player owner, Long xp, Boolean useProtocolLib, LanguageManager languageManager) {
        super(owner, xp, useProtocolLib, languageManager);
    }

}
