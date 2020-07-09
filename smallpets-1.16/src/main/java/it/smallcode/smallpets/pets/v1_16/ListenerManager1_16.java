package it.smallcode.smallpets.pets.v1_16;
/*

Class created by SmallCode
09.07.2020 17:48

*/

import it.smallcode.smallpets.manager.InventoryCache;
import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.pets.v1_15.ListenerManager1_15;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager1_16 extends ListenerManager1_15 {

    public ListenerManager1_16(JavaPlugin plugin, UserManager userManager, PetMapManager petMapManager, InventoryCache inventoryCache, String prefix, double xpMultiplier) {
        super(plugin, userManager, petMapManager, inventoryCache, prefix, xpMultiplier);
    }
}
