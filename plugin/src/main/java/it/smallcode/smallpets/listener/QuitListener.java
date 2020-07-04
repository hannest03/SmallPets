package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
03.07.2020 14:10

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        Player p = e.getPlayer();

        User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

        if(user != null)
            user.despawnSelected();

        SmallPets.getInstance().getInventoryCache().removeInventory(p);

    }

}
