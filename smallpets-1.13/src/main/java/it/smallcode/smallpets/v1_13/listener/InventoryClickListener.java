package it.smallcode.smallpets.v1_13.listener;
/*

Class created by SmallCode
31.08.2020 21:02

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.InventoryManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryClickListener implements Listener {

    private UserManager userManager;
    private LanguageManager languageManager;
    private JavaPlugin plugin;

    private InventoryManager inventoryManager;

    private String prefix;

    public InventoryClickListener(UserManager userManager, String prefix, LanguageManager languageManager, JavaPlugin plugin, InventoryManager inventoryManager){

        this.userManager = userManager;
        this.languageManager = languageManager;
        this.plugin = plugin;

        this.inventoryManager = inventoryManager;

        this.prefix = prefix;

    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(e.getView().getTitle().equals("§eSmallPets")){

            Player p = (Player) e.getWhoClicked();

            e.setCancelled(true);

            if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                if (e.getCurrentItem().getType() == Material.PLAYER_HEAD) {

                    net.minecraft.server.v1_13_R2.ItemStack item = CraftItemStack.asNMSCopy(e.getCurrentItem());

                    if(item.getTag() == null)
                        return;

                    if(!item.getTag().hasKey("pet"))
                        return;

                    String type = item.getTag().getString("pet");

                    if(type != null && type.trim().length() != 0){

                        User user = userManager.getUser(p.getUniqueId().toString());

                        if(user != null) {

                            if(!inventoryManager.getConvertingPets().contains(p.getUniqueId().toString())) {

                                if (user.getSelected() != null && user.getSelected().getName().equals(type)) {

                                    user.setSelected(null);

                                    e.getWhoClicked().sendMessage(prefix + languageManager.getLanguage().getStringFormatted("petDespawned"));

                                } else {

                                    user.setSelected(user.getPetFromType(type));

                                    e.getWhoClicked().sendMessage(prefix + languageManager.getLanguage().getStringFormatted("petSpawned"));

                                }

                            }else{

                                if(p.getInventory().firstEmpty() != -1) {

                                    if(user.getSelected() != null) {

                                        if (user.getSelected().getName().equalsIgnoreCase(type)) {

                                            user.setSelected(null);

                                        }
                                    }

                                    ItemStack petItem = user.getPetFromType(type).getUnlockItem(plugin);

                                    userManager.removeUserPet(type, p.getUniqueId().toString());

                                    p.getInventory().addItem(petItem);

                                    p.closeInventory();

                                }else{

                                    p.sendMessage(prefix + languageManager.getLanguage().getStringFormatted("inventoryFull"));

                                }

                            }

                        }

                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1,1 );

                        p.closeInventory();

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
