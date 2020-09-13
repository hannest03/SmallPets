package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
03.07.2020 16:40

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.InventoryCache;
import it.smallcode.smallpets.core.manager.InventoryManager;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InventoryManager1_15 extends InventoryManager {

    public InventoryManager1_15(InventoryCache inventoryCache, LanguageManager languageManager, double xpMultiplier, JavaPlugin plugin) {

        super(inventoryCache, languageManager, xpMultiplier, plugin);

    }

    @Override
    public void openPetsMenu(List<Pet> pets, Player p) {

        Inventory inventory = inventoryCache.getInventory(p);

        inventory.clear();

        inventory = makeEdges(inventory);

        inventory.setItem(0, createItem("§8Select", Material.BLACK_STAINED_GLASS_PANE));

        for(Pet pet : pets){

            ItemStack itemStack = makePetItem(pet, p);

            if(itemStack != null) {

                inventory.addItem(itemStack);

            }

        }

        if(convertingPets.contains(p.getUniqueId().toString())){

            inventory.setItem(44, createItem(languageManager.getLanguage().getStringFormatted("convertToItem"), Material.LIME_DYE));

        }else{

            inventory.setItem(44, createItem(languageManager.getLanguage().getStringFormatted("convertToItem"), Material.GRAY_DYE));

        }

        ItemStack stats = new ItemStack(Material.REDSTONE_TORCH);

        ItemMeta itemMeta = stats.getItemMeta();

        itemMeta.setDisplayName("§6" + languageManager.getLanguage().getStringFormatted("stats"));

        List<String> lore = new LinkedList<>();

        double experienceMultiplier = (int) (xpMultiplier * 100D) /100D;

        lore.add("");
        lore.add("§e" + languageManager.getLanguage().getStringFormatted("experienceMultiplier") + "§8: §7" + experienceMultiplier);
        lore.add("");

        itemMeta.setLore(lore);

        stats.setItemMeta(itemMeta);

        inventory.setItem(39, createItem("§6Sort", Material.HOPPER, "§cNot implemented yet"));
        inventory.setItem(40, stats);
        inventory.setItem(41, createItem("§6Recipe book", Material.BOOK, "§cNot implemented yet"));

        p.openInventory(inventory);

    }

    private ItemStack makePetItem(Pet pet, Player p){

        ItemStack itemStack = pet.getDisplayItem(plugin);

        if(itemStack != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();

            List<String> lore = itemMeta.getLore();

            lore.add("");

            if(pet.isActivated())
                lore.add(languageManager.getLanguage().getStringFormatted("clickToDeselect"));
            else
                lore.add(languageManager.getLanguage().getStringFormatted("clickToSelect"));

            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);

        }

        return itemStack;

    }

    private Inventory makeEdges(Inventory inventory){

        for(int i = 0; i < 9; i++){

            inventory.setItem(i, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        }

        for(int i = 36; i < 45; i++){

            inventory.setItem(i, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        }

        inventory.setItem(9, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(9 + 8, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        inventory.setItem(9 * 2, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(9 * 2 + 8, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        inventory.setItem(9 * 3, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(9 * 3 + 8, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        return inventory;

    }

    private ItemStack createItem(String name, Material material){

        ItemStack item = new ItemStack(material);

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);

        item.setItemMeta(itemMeta);

        return item;

    }

    private ItemStack createItem(String name, Material material, String lore){

        ItemStack item = new ItemStack(material);

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);

        ArrayList<String> finalLore = new ArrayList<>();

        String[] loreArray = lore.split("\n");

        for(String string : loreArray){

            finalLore.add(string);

        }

        itemMeta.setLore(finalLore);

        item.setItemMeta(itemMeta);

        return item;

    }

}
