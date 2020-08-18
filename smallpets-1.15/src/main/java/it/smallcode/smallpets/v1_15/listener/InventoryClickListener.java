package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
03.07.2020 18:58

*/

import it.smallcode.smallpets.languages.Language;
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class InventoryClickListener implements Listener {

    private UserManager userManager;
    private LanguageManager languageManager;
    private JavaPlugin plugin;

    private String prefix;

    public InventoryClickListener(UserManager userManager, String prefix, LanguageManager languageManager, JavaPlugin plugin){

        this.userManager = userManager;
        this.languageManager = languageManager;
        this.plugin = plugin;

        this.prefix = prefix;

    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(e.getView().getTitle().equals("§eSmallPets")){

            e.setCancelled(true);

            if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                if (e.getCurrentItem().getType() == Material.PLAYER_HEAD) {

                    String type = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "pet"), PersistentDataType.STRING);

                    if(type != null && type.trim().length() != 0){

                        Player p = (Player) e.getWhoClicked();

                        User user = userManager.getUser(p.getUniqueId().toString());

                        if(user != null) {

                            if (user.getSelected() != null && user.getSelected().getName().equals(type)) {

                                user.setSelected(null);

                                e.getWhoClicked().sendMessage(prefix + languageManager.getLanguage().getStringFormatted("petDespawned"));

                            } else {

                                user.setSelected(user.getPetFromType(type));

                                e.getWhoClicked().sendMessage(prefix + languageManager.getLanguage().getStringFormatted("petSpawned"));

                            }

                        }

                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1,1 );

                        p.closeInventory();

                    }

                }

            }

        }

    }

    private String getRealTypeFromType(String type){

        for (Map.Entry<String, String> entry : languageManager.getLanguage().getTranslations().entrySet()) {
            if (type.equals(entry.getValue())) {

                if(entry.getKey().startsWith("translations.pet")){

                    return entry.getKey().replace("translations.pet.", "");

                }

            }
        }

        return "";

    }

}
