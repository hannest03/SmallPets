package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
03.07.2020 16:40

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.manager.InventoryManager;
import it.smallcode.smallpets.core.manager.SortManager;
import it.smallcode.smallpets.core.manager.types.Sort;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.recipe.Recipe;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryManager1_15 extends InventoryManager {

    public InventoryManager1_15(double xpMultiplier) {

        super(xpMultiplier);

    }

    @Override
    public void openPetsMenu(int page, Player p) {

        Inventory inventory = SmallPetsCommons.getSmallPetsCommons().getInventoryCache().getInventory(p);

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        inventory.clear();

        inventory = makeEdges(inventory);

        ItemStack first = createItem("§8 ", Material.BLACK_STAINED_GLASS_PANE);
        first = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(first, "invType", "select");
        first = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(first, "page", String.valueOf(page));

        inventory.setItem(0, first);

        List<Pet> allPets = SmallPetsCommons.getSmallPetsCommons().getSortManager().getPetsSorted(user, user.getPets());
        List<Pet> pets = new LinkedList<>();

        int availableSlots = 0;
        for(ItemStack itemStack : inventory.getContents())
            if(itemStack == null)
                availableSlots++;

        int firstIndex = availableSlots * (page);
        int lastIndex = (availableSlots * (page +1)) -1;

        for(int i = firstIndex; i <= lastIndex; i++){
            if(i == allPets.size())
                break;
            pets.add(allPets.get(i));
        }

        for(Pet pet : pets){

            ItemStack itemStack = makePetItem(pet, user);

            if(itemStack != null) {

                inventory.addItem(itemStack);

            }

        }

        if(convertingPets.contains(p.getUniqueId().toString())){

            inventory.setItem(53, createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("convertToItem"), Material.LIME_DYE));

        }else{

            inventory.setItem(53, createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("convertToItem"), Material.GRAY_DYE));

        }

        if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()){

            ItemStack item;

            if(user.getSettings().isShowPets()) {

                item = createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("visibility.hidePets"), Material.STONE_BUTTON);

            }else{

                item =  createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("visibility.showPets"), Material.STONE_BUTTON);

            }

            item = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(item, "showPets", String.valueOf(user.getSettings().isShowPets()));

            inventory.setItem(45, item);

        }

        ItemStack stats = new ItemStack(Material.REDSTONE_TORCH);

        ItemMeta itemMeta = stats.getItemMeta();

        itemMeta.setDisplayName("§6" + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("stats"));

        List<String> lore = new LinkedList<>();

        double experienceMultiplier = (int) (xpMultiplier * 100D) /100D;

        lore.add("");
        lore.add("§e" + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("experienceMultiplier") + "§8: §7" + experienceMultiplier);
        lore.add("");

        itemMeta.setLore(lore);

        stats.setItemMeta(itemMeta);

        if(firstIndex >= availableSlots){
            ItemStack prevItemStack = createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.previous.name"), Material.ARROW, SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.previous.description"));
            prevItemStack = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(prevItemStack, "inv.action", "prev");
            inventory.setItem(47, prevItemStack);
        }

        if(lastIndex < allPets.size()-1){
            ItemStack nextItemStack = createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.next.name"), Material.ARROW, SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.next.description"));
            nextItemStack = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(nextItemStack, "inv.action", "next");
            inventory.setItem(51, nextItemStack);
        }

        inventory.setItem(48, createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.sort.name"), Material.HOPPER, SmallPetsCommons.getSmallPetsCommons().getSortManager().sortLore(user)));
        inventory.setItem(49, stats);
        inventory.setItem(50, createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.recipebook.name"), Material.BOOK, SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.recipebook.description")));

        p.openInventory(inventory);

    }

    @Override
    public void openRecipeBook(int page, Player p) {
        Inventory inventory = SmallPetsCommons.getSmallPetsCommons().getInventoryCache().getInventory(p);
        inventory.clear();
        inventory = makeEdges(inventory);

        ItemStack first = createItem("§8 ", Material.BLACK_STAINED_GLASS_PANE);
        first = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(first, "invType", "recipeBook");
        first = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(first, "page", String.valueOf(page));

        inventory.setItem(0, first);

        //TODO: Change with better petmap
        List<Pet> allPets = SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().keySet()
                .stream()
                .map(id -> PetFactory.createPet(id, null, null, 0L))
                .filter(pet -> pet.getRecipe() != null)
                .collect(Collectors.toList());
        List<Pet> pets = new LinkedList<>();

        int availableSlots = 0;
        for(ItemStack itemStack : inventory.getContents())
            if(itemStack == null)
                availableSlots++;

        int firstIndex = availableSlots * (page);
        int lastIndex = (availableSlots * (page +1)) -1;

        for(int i = firstIndex; i <= lastIndex; i++){
            if(i == allPets.size())
                break;
            pets.add(allPets.get(i));
        }

        for(Pet pet : pets){
            ItemStack itemStack = makeRecipeBookPet(pet);
            if(itemStack != null) {
                inventory.addItem(itemStack);
            }
        }
        if(firstIndex >= availableSlots){
            ItemStack prevItemStack = createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.previous.name"), Material.ARROW, SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.previous.description"));
            prevItemStack = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(prevItemStack, "inv.action", "prev");
            inventory.setItem(47, prevItemStack);
        }

        if(lastIndex < allPets.size()-1){
            ItemStack nextItemStack = createItem(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.next.name"), Material.ARROW, SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.next.description"));
            nextItemStack = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(nextItemStack, "inv.action", "next");
            inventory.setItem(51, nextItemStack);
        }

        inventory.setItem(49, createItem(
                SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.back.name"),
                Material.ARROW,
                SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.back.description")
        ));

        p.openInventory(inventory);
    }

    @Override
    public void openRecipe(String petID, Player p) {
        Inventory inventory = SmallPetsCommons.getSmallPetsCommons().getInventoryCache().getInventory(p);
        inventory.clear();
        for(int i = 0; i < 54; i++){
            inventory.setItem(i, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        }

        ItemStack first = createItem("§8 ", Material.BLACK_STAINED_GLASS_PANE);
        first = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(first, "invType", "recipe");
        inventory.setItem(0, first);

        Pet pet = PetFactory.createPet(petID, null, null, 0L);
        inventory = fillRecipe(pet.getRecipe(), inventory);
        inventory.setItem(24, pet.getUnlockItem());

        inventory.setItem(49, createItem(
                SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.back.name"),
                Material.ARROW,
                SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.back.description")
        ));

        p.openInventory(inventory);

    }

    private Inventory fillRecipe(Recipe recipe, Inventory inventory){
        ItemStack[] items = recipe.getItems();

        //First row
        if(items[0] != null)
            inventory.setItem(11, items[0]);
        else
            inventory.setItem(11, new ItemStack(Material.AIR));

        if(items[1] != null)
            inventory.setItem(12, items[1]);
        else
            inventory.setItem(12, new ItemStack(Material.AIR));

        if(items[2] != null)
            inventory.setItem(13, items[2]);
        else
            inventory.setItem(13, new ItemStack(Material.AIR));

        //Second row
        if(items[3] != null)
            inventory.setItem(20, items[3]);
        else
            inventory.setItem(20, new ItemStack(Material.AIR));

        if(items[4] != null)
            inventory.setItem(21, items[4]);
        else
            inventory.setItem(21, new ItemStack(Material.AIR));

        if(items[5] != null)
            inventory.setItem(22, items[5]);
        else
            inventory.setItem(22, new ItemStack(Material.AIR));

        //Third row
        if(items[6] != null)
            inventory.setItem(29, items[6]);
        else
            inventory.setItem(29, new ItemStack(Material.AIR));

        if(items[7] != null)
            inventory.setItem(30, items[7]);
        else
            inventory.setItem(30, new ItemStack(Material.AIR));

        if(items[8] != null)
            inventory.setItem(31, items[8]);
        else
            inventory.setItem(31, new ItemStack(Material.AIR));

        return inventory;
    }

    private ItemStack makePetItem(Pet pet, User user){

        ItemStack itemStack = pet.getDisplayItem();

        if(itemStack != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();

            List<String> lore = itemMeta.getLore();

            lore.add("");

            if(user.getSelected() == pet)
                lore.add(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("clickToDeselect"));
            else
                lore.add(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("clickToSelect"));

            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);

        }

        return itemStack;

    }

    private ItemStack makeRecipeBookPet(Pet pet){
        ItemStack itemStack = pet.getDisplayItem();
        if(itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = itemMeta.getLore();
            lore.add("");
            lore.add(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("clickToShowRecipe"));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    private Inventory makeEdges(Inventory inventory){

        for(int i = 0; i < 9; i++){

            inventory.setItem(i, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        }

        for(int i = 45; i < 54; i++){

            inventory.setItem(i, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        }

        inventory.setItem(9, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(9 + 8, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        inventory.setItem(9 * 2, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(9 * 2 + 8, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        inventory.setItem(9 * 3, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(9 * 3 + 8, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

        inventory.setItem(9 * 4, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(9 * 4 + 8, createItem("§8", Material.BLACK_STAINED_GLASS_PANE));

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
