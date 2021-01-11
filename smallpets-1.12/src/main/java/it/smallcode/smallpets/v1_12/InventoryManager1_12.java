package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
09.07.2020 18:40

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.InventoryManager;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InventoryManager1_12 extends InventoryManager {

    /**
     * Creates a inventory manager object
     *
     */
    public InventoryManager1_12(double xpMultiplier) {

        super(xpMultiplier);

    }

    @Override
    public void openPetsMenu(List<Pet> pets, Player p) {
        Inventory inventory = SmallPetsCommons.getSmallPetsCommons().getInventoryCache().getInventory(p);

        inventory.clear();

        inventory = makeEdges(inventory);

        for(Pet pet : pets){

            ItemStack itemStack = makePetItem(pet, p);

            if(itemStack != null) {

                inventory.addItem(itemStack);

            }

        }

        if(convertingPets.contains(p.getUniqueId().toString())){

            inventory.setItem(44, createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("convertToItem"), 351, (short) 10));

        }else{

            inventory.setItem(44, createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("convertToItem"), 351, (short) 8));

        }

        ItemStack stats = new ItemStack(Material.REDSTONE_TORCH_ON);

        ItemMeta itemMeta = stats.getItemMeta();

        itemMeta.setDisplayName("§6" + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("stats"));

        List<String> lore = new LinkedList<>();

        lore.add("");
        lore.add("§e" + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("experienceMultiplier") + "§8: §7" + ((int) (xpMultiplier *100)) / 100);
        lore.add("");

        itemMeta.setLore(lore);

        stats.setItemMeta(itemMeta);

        inventory.setItem(39, createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.sort.name"), 154, (short) 0, SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.sort.description")));
        inventory.setItem(40, stats);
        inventory.setItem(41, createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.recipebook.name"), 340, (short) 0, SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.recipebook.description")));

        p.openInventory(inventory);

    }

    private ItemStack makePetItem(Pet pet, Player p){

        ItemStack itemStack = pet.getDisplayItem();

        if(itemStack != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();

            List<String> lore = itemMeta.getLore();

            lore.add("");

            if(pet.isActivated())
                lore.add(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("clickToDeselect"));
            else
                lore.add(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("clickToSelect"));

            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);

        }

        return itemStack;

    }

    private Inventory makeEdges(Inventory inventory){

        for(int i = 0; i < 9; i++){

            inventory.setItem(i, createItem("§8", 160, (short) 15));

        }

        for(int i = 36; i < 45; i++){

            inventory.setItem(i, createItem("§8", 160, (short) 15));

        }

        inventory.setItem(9, createItem("§8", 160, (short) 15));
        inventory.setItem(9 + 8, createItem("§8", 160, (short) 15));

        inventory.setItem(9 * 2, createItem("§8", 160, (short) 15));
        inventory.setItem(9 * 2 + 8, createItem("§8", 160, (short) 15));

        inventory.setItem(9 * 3, createItem("§8", 160, (short) 15));
        inventory.setItem(9 * 3 + 8, createItem("§8", 160, (short) 15));

        return inventory;

    }

    private ItemStack createItem(String name, int id, short subid){

        ItemStack item = new ItemStack(id, 1, subid);

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);

        item.setItemMeta(itemMeta);

        return item;

    }

    private ItemStack createItem(String name, int id, short subid, String lore){

        ItemStack item = new ItemStack(id, 1, subid);

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
