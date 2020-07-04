package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
04.07.2020 13:42

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.events.PetChangeWorldEvent;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e){

        User user = SmallPets.getInstance().getUserManager().getUser(e.getPlayer().getUniqueId().toString());

        if(user != null){

            if(user.getSelected() != null){

                PetChangeWorldEvent event = new PetChangeWorldEvent(user.getSelected(), e.getPlayer());

                Bukkit.getPluginManager().callEvent(event);

                if(!event.isCancelled()) {

                    Location loc = e.getPlayer().getLocation().clone();

                    loc.setX(loc.getX() - 1);
                    loc.setY(loc.getY() + 0.75);

                    user.getSelected().getArmorStand().teleport(loc);

                }else{

                    user.getSelected().destroy();

                }
            }

        }

    }

}
