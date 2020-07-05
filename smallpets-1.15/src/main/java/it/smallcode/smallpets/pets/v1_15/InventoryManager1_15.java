package it.smallcode.smallpets.pets.v1_15;
/*

Class created by SmallCode
03.07.2020 16:40

*/

import it.smallcode.smallpets.manager.InventoryCache;
import it.smallcode.smallpets.manager.InventoryManager;
import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.text.CenteredText;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager1_15 extends InventoryManager {


    public InventoryManager1_15(InventoryCache inventoryCache) {
        super(inventoryCache);
    }

    @Override
    public void openPetsMenu(List<Pet> pets, Player p) {

        Inventory inventory = inventoryCache.getInventory(p);

        inventory.clear();

        inventory = makeEdges(inventory);

        for(Pet pet : pets){

            ItemStack itemStack = makePetItem(pet, p);

            if(itemStack != null) {

                inventory.addItem(itemStack);

            }

        }

        p.openInventory(inventory);

    }

    private ItemStack makePetItem(Pet pet, Player p){

        ItemStack itemStack = pet.getItem();

        if(itemStack != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName("§e" + p.getName() + "s " + pet.getName());

            ArrayList<String> lore = new ArrayList();

            lore.add("");

            String progressBar = generateFinishedProgressbar(pet);

            if(pet.getLevel() != 100)
                lore.add("  " + CenteredText.sendCenteredMessage("§b" + pet.getLevel(), ChatColor.stripColor(progressBar).length()));
            else
                lore.add(CenteredText.sendCenteredMessage("§b" + pet.getLevel(), ChatColor.stripColor(progressBar).length()));

            lore.add(progressBar);

            String expB = "§c§kS§c MAX LEVEL §c§kS";

            if(pet.getLevel() != 100) {
                expB = "§7" + pet.getXp() + "§8/§7" + pet.getExpForNextLevel();

                lore.add("  " + CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

            }else
                lore.add(CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

            lore.add("");

            if(pet.getArmorStand() != null && pet.getArmorStand().isValid())
                lore.add("§cCLICK TO DESELECT");
            else
                lore.add("§6CLICK TO SELECT");

            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);

        }

        return itemStack;

    }

    private String generateFinishedProgressbar(Pet pet){

        if(pet.getLevel() == 100)
            return generateProgressBar(pet.getExpForLevel(pet.getLevel()), pet.getXp(), pet.getExpForNextLevel());

        return "§b" + pet.getLevel() + " " + generateProgressBar(pet.getExpForLevel(pet.getLevel()), pet.getXp(), pet.getExpForNextLevel()) + " §b" + (pet.getLevel() +1);

    }

    private String generateProgressBar(int lastExp, int exp, int nextExp){

        String bar = "";

        int bars = 35;

        if(nextExp == 0){

            for(int i = 0; i <= bars; i++)
                bar += "§c|";

            return bar;

        }

        int oneBar = (nextExp - lastExp) / bars;

        int nextBar = 0;

        bar += "§b|";

        while(nextBar <= (exp - lastExp) && bar.length() < (bars*3)){

            nextBar += oneBar;

            bar += "§b|";

        }

        while(bar.length() < (bars*3)){

            bar += "§8|";

        }

        return bar;

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
