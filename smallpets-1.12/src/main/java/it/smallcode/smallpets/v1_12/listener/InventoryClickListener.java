package it.smallcode.smallpets.v1_12.listener;
/*

Class created by SmallCode
09.07.2020 21:33

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.InventoryManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.Sort;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(e.getView().getTitle().equals(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.name"))){

            e.setCancelled(true);

            UserManager userManager = SmallPetsCommons.getSmallPetsCommons().getUserManager();
            InventoryManager inventoryManager = SmallPetsCommons.getSmallPetsCommons().getInventoryManager();
            LanguageManager languageManager = SmallPetsCommons.getSmallPetsCommons().getLanguageManager();

            if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {


                if(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(e.getCurrentItem(), "showPets")){

                    Player p = (Player) e.getWhoClicked();

                    User user = userManager.getUser(p.getUniqueId().toString());

                    user.getSettings().setShowPets(!user.getSettings().isShowPets());
                    userManager.updatePets(p);

                    p.closeInventory();
                    return;

                }

                if (e.getCurrentItem().getTypeId() == 397) {

                    Player p = (Player) e.getWhoClicked();

                    if(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(e.getCurrentItem(), "pet") &&
                            SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(e.getCurrentItem(), "pet.uuid")){

                        UUID petUUID = UUID.fromString(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().getNBTTagValue(e.getCurrentItem(), "pet.uuid"));

                        User user = userManager.getUser(p.getUniqueId().toString());

                        if(user != null) {

                            if(!inventoryManager.getConvertingPets().contains(p.getUniqueId().toString())) {

                                if (user.getSelected() != null && user.getSelected().getUuid().equals(petUUID)) {

                                    if(user.setSelected(null)) {

                                        e.getWhoClicked().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + languageManager.getLanguage().getStringFormatted("petDespawned"));

                                    }

                                } else {

                                    if (user.setSelected(user.getPetFromUUID(petUUID))) {

                                        e.getWhoClicked().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + languageManager.getLanguage().getStringFormatted("petSpawned"));

                                    }

                                }

                            }else{

                                if(p.getInventory().firstEmpty() != -1) {

                                    if(user.getSelected() != null) {

                                        if (user.getSelected().getUuid().equals(petUUID)) {

                                            user.setSelected(null);

                                        }

                                    }

                                    ItemStack petItem = user.getPetFromUUID(petUUID).getUnlockItem();

                                    userManager.removeUserPet(petUUID, p.getUniqueId().toString());

                                    p.getInventory().addItem(petItem);

                                    p.closeInventory();

                                }else{

                                    p.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + languageManager.getLanguage().getStringFormatted("inventoryFull"));

                                }

                            }

                        }

                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1,1 );

                        p.closeInventory();

                    }

                }else if(e.getCurrentItem().getTypeId() == 351 && (e.getCurrentItem().getDurability() == 8 || e.getCurrentItem().getDurability() == 10)){

                    if(!inventoryManager.getConvertingPets().contains(e.getWhoClicked().getUniqueId().toString())){

                        inventoryManager.getConvertingPets().add(e.getWhoClicked().getUniqueId().toString());

                        e.getInventory().setItem(44, createItem(languageManager.getLanguage().getStringFormatted("convertToItem"), 351, 10));

                    }else{

                        inventoryManager.getConvertingPets().remove(e.getWhoClicked().getUniqueId().toString());

                        e.getInventory().setItem(44, createItem(languageManager.getLanguage().getStringFormatted("convertToItem"), 351, 8));

                    }

                }


                if(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(e.getCurrentItem(), "inv.action")){
                    String action = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().getNBTTagValue(e.getCurrentItem(), "inv.action");
                    int page = 0;

                    ItemStack itemStack = e.getInventory().getItem(0);

                    if(itemStack == null)
                        return;

                    if(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(itemStack, "page"))
                        page = Integer.parseInt(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().getNBTTagValue(itemStack, "page"));

                    if(action.equals("next")){
                        SmallPetsCommons.getSmallPetsCommons().getInventoryManager().openPetsMenu(page+1, (Player) e.getWhoClicked());
                    }else if(action.equals("prev")){
                        SmallPetsCommons.getSmallPetsCommons().getInventoryManager().openPetsMenu(page-1, (Player) e.getWhoClicked());
                    }
                }

                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.sort.name"))){
                    Player p = (Player) e.getWhoClicked();

                    User user = userManager.getUser(p.getUniqueId().toString());
                    ItemStack itemStack = e.getInventory().getItem(0);

                    int page = 0;
                    if(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(itemStack, "page"))
                        page = Integer.parseInt(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().getNBTTagValue(itemStack, "page"));

                    if(e.getClick() == ClickType.LEFT){
                        Sort sort = SmallPetsCommons.getSmallPetsCommons().getSortManager().getNextSort(user.getSettings().getSort());
                        user.getSettings().setSort(sort);
                    }

                    if(e.getClick() == ClickType.RIGHT){
                        Sort sort = SmallPetsCommons.getSmallPetsCommons().getSortManager().getPreviousSort(user.getSettings().getSort());
                        user.getSettings().setSort(sort);
                    }

                    SmallPetsCommons.getSmallPetsCommons().getInventoryManager().openPetsMenu(page, p);
                }

            }

        }

    }

    private ItemStack createItem(String name, int id, int subid){

        ItemStack item = new ItemStack(id, 1, (short) subid);

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);

        item.setItemMeta(itemMeta);

        return item;

    }

}
