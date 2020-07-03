package it.smallcode.smallpets.pets.v1_15;
/*

Class created by SmallCode
03.07.2020 16:40

*/

import it.smallcode.smallpets.manager.InventoryCache;
import it.smallcode.smallpets.manager.InventoryManager;
import it.smallcode.smallpets.pets.Pet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InventoryManager1_15 extends InventoryManager {


    public InventoryManager1_15(InventoryCache inventoryCache) {
        super(inventoryCache);
    }

    @Override
    public void openPetsMenu(ArrayList<Pet> pets, Player p) {

        Inventory inventory = inventoryCache.getInventory(p);

        inventory.clear();

        inventory = makeEdges(inventory);

        for(Pet pet : pets){

            ItemStack itemStack = pet.getItem();

            if(itemStack != null) {

                String type = itemStack.getItemMeta().getDisplayName();

                ItemMeta itemMeta = itemStack.getItemMeta();

                itemMeta.setDisplayName("§e" + p.getName() + "s " + type);

                ArrayList<String> lore = new ArrayList();

                lore.add("");
                lore.add("§6CLICK TO SUMMON");

                itemMeta.setLore(lore);

                itemStack.setItemMeta(itemMeta);

                inventory.addItem(itemStack);

            }

        }

        p.openInventory(inventory);

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

}
