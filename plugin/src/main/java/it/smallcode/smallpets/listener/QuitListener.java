package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
03.07.2020 14:10

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.JoinEvent;
import it.smallcode.smallpets.core.abilities.eventsystem.events.QuitEvent;
import it.smallcode.smallpets.core.manager.InventoryCache;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private UserManager userManager;
    private InventoryCache inventoryCache;

    public QuitListener(UserManager userManager, InventoryCache inventoryCache){

        this.userManager = userManager;
        this.inventoryCache = inventoryCache;

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        Player p = e.getPlayer();

        User user = userManager.getUser(p.getUniqueId().toString());

        if(user != null) {

            user.despawnSelected();

            if(user.getSelected() != null){

                AbilityEventBus.post(new QuitEvent(user));

            }
            SmallPets.getInstance().getUserManager().saveUserAndRemoveFromCache(user);
        }

        inventoryCache.removeInventory(p);

    }

}
