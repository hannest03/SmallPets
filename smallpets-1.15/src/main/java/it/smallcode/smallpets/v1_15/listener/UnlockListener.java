package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
05.07.2020 18:59

*/

import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.InventoryCache;
import it.smallcode.smallpets.manager.UserManager;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class UnlockListener implements Listener {

    private JavaPlugin plugin;

    private LanguageManager languageManager;
    private UserManager userManager;
    private String prefix;

    public UnlockListener(JavaPlugin plugin, LanguageManager languageManager, UserManager userManager, String prefix){

        this.plugin = plugin;

        this.languageManager = languageManager;
        this.userManager = userManager;
        this.prefix = prefix;

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)){

            if(e.getItem() != null){

                ItemStack item = e.getItem();

                if(item.getItemMeta() != null){

                    ItemMeta itemMeta = item.getItemMeta();

                    if(itemMeta.getPersistentDataContainer() != null){

                        NamespacedKey key = new NamespacedKey(plugin, "pet");

                        if(itemMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING)){

                            String type = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);

                            if(userManager.giveUserPet(type, e.getPlayer().getUniqueId().toString())){

                                e.getItem().setAmount(e.getItem().getAmount() -1);

                                e.getPlayer().sendMessage(prefix + languageManager.getLanguage().getStringFormatted("petUnlock")
                                        .replaceAll("%pet_type%", languageManager.getLanguage().getStringFormatted("pet." + type)));

                            }

                        }

                    }

                }

            }

        }

    }

}
