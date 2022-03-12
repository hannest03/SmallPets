package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
03.07.2020 18:58

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.InventoryManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.Sort;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(e.getView().getTitle().equals(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.name"))){

            Player p = (Player) e.getWhoClicked();

            UserManager userManager = SmallPetsCommons.getSmallPetsCommons().getUserManager();
            InventoryManager inventoryManager = SmallPetsCommons.getSmallPetsCommons().getInventoryManager();
            LanguageManager languageManager = SmallPetsCommons.getSmallPetsCommons().getLanguageManager();

            e.setCancelled(true);

            if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                if(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(e.getCurrentItem(), "showPets")){

                    User user = userManager.getUser(p.getUniqueId().toString());

                    user.getSettings().setShowPets(!user.getSettings().isShowPets());
                    userManager.updatePets(p);

                    p.closeInventory();
                    return;

                }

                if (e.getCurrentItem().getType() == Material.PLAYER_HEAD) {

                    if(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(e.getCurrentItem(), "pet") &&
                            SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(e.getCurrentItem(), "pet.uuid")){

                        UUID uuid = UUID.fromString(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().getNBTTagValue(e.getCurrentItem(), "pet.uuid"));

                        if(uuid != null){

                            User user = userManager.getUser(p.getUniqueId().toString());

                            if(user != null) {

                                if(!inventoryManager.getConvertingPets().contains(p.getUniqueId().toString())) {

                                    if (user.getSelected() != null && user.getSelected().getUuid().equals(uuid)) {

                                        if(user.setSelected(null)) {

                                            e.getWhoClicked().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + languageManager.getLanguage().getStringFormatted("petDespawned"));

                                        }

                                    } else {

                                        if(user.setSelected(user.getPetFromUUID(uuid))) {

                                            e.getWhoClicked().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + languageManager.getLanguage().getStringFormatted("petSpawned"));

                                        }

                                    }

                                }else{

                                    if(p.getInventory().firstEmpty() != -1) {

                                        if(user.getSelected() != null) {

                                            if (user.getSelected().getUuid().equals(uuid)) {

                                                user.setSelected(null);

                                            }
                                        }

                                        ItemStack petItem = user.getPetFromUUID(uuid).getUnlockItem();

                                        userManager.removeUserPet(uuid, p.getUniqueId().toString());

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

                    }

                }else if(e.getCurrentItem().getType() == Material.GRAY_DYE || e.getCurrentItem().getType() == Material.LIME_DYE){

                    if(!inventoryManager.getConvertingPets().contains(e.getWhoClicked().getUniqueId().toString())){

                        inventoryManager.getConvertingPets().add(e.getWhoClicked().getUniqueId().toString());

                        e.getInventory().setItem(44, createItem(languageManager.getLanguage().getStringFormatted("convertToItem"), Material.LIME_DYE));

                    }else{

                        inventoryManager.getConvertingPets().remove(e.getWhoClicked().getUniqueId().toString());

                        e.getInventory().setItem(44, createItem(languageManager.getLanguage().getStringFormatted("convertToItem"), Material.GRAY_DYE));

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
                        SmallPetsCommons.getSmallPetsCommons().getInventoryManager().openPetsMenu(page+1, p);
                    }else if(action.equals("prev")){
                        SmallPetsCommons.getSmallPetsCommons().getInventoryManager().openPetsMenu(page-1, p);
                    }
                }

                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("inventory.sort.name"))){
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

    private ItemStack createItem(String name, Material material){

        ItemStack item = new ItemStack(material);

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);

        item.setItemMeta(itemMeta);

        return item;

    }

}
