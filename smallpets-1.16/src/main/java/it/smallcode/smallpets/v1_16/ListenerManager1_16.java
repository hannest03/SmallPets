package it.smallcode.smallpets.v1_16;
/*

Class created by SmallCode
09.07.2020 17:48

*/

import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.InventoryCache;
import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.v1_15.ListenerManager1_15;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager1_16 extends ListenerManager1_15 {

    public ListenerManager1_16(JavaPlugin plugin, UserManager userManager, PetMapManager petMapManager, LanguageManager languageManager, InventoryCache inventoryCache, String prefix, double xpMultiplier, boolean useProtocollib) {
        super(plugin, userManager, petMapManager, languageManager, inventoryCache, prefix, xpMultiplier, useProtocollib);
    }
}
