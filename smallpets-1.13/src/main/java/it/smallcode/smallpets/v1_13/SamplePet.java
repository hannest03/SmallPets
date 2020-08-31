package it.smallcode.smallpets.v1_13;
/*

Class created by SmallCode
01.08.2020 21:48

*/

import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.text.CenteredText;
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

    /**
     *
     * Returns the item to unlock the tiger
     *
     * @param plugin - the plugin
     * @return the item to unlock the tiger
     */
    @Override
    public ItemStack getUnlockItem(Plugin plugin) {

        ItemStack item = getDisplayItem((JavaPlugin) plugin);

        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = itemMeta.getLore();

        lore.add("");
        lore.add("§6RIGHT CLICK TO UNLOCK");

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        item = addNBTTag(item, "petExp", String.valueOf(getXp()));

        return item;

    }

    @Override
    public ItemStack getDisplayItem(JavaPlugin plugin) {

        ItemStack itemStack = getItem();

        if(itemStack != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(getCustomeNameWithoutPlayername());

            ArrayList<String> lore = new ArrayList();

            if(getPetType() != null) {

                lore.add("§8" + getPetType().getName(getLanguageManager()));

            }

            lore.add("");

            lore.add(getAbility());

            lore.add("");

            String progressBar = CenteredText.sendCenteredMessage(generateFinishedProgressbar(), ChatColor.stripColor(getAbility()).length());

            if(getLevel() != 100) {

                lore.add("  " + CenteredText.sendCenteredMessage(getLevelColor() + getLevel(), ChatColor.stripColor(progressBar).length()));
                lore.add(progressBar);

                String expB = getLevelColor() + (getXp() - getExpForLevel(getLevel())) + "§8/" + getLevelColor() + (getExpForNextLevel() - getExpForLevel(getLevel()));

                lore.add("  " + CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

            }else{

                lore.add("§8" + getLanguageManager().getLanguage().getStringFormatted("maxLevel"));

            }

            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);

            itemStack = addNBTTag(itemStack, "pet", getName());

        }

        return itemStack;

    }

    private ItemStack addNBTTag(ItemStack itemStack, String key, String value){

        net.minecraft.server.v1_13_R2.ItemStack item = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tag = item.getTag() != null ? item.getTag() : new NBTTagCompound();

        tag.setString(key, value);

        item.setTag(tag);

        itemStack = CraftItemStack.asCraftMirror(item);

        return itemStack;

    }

}
