package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
04.07.2020 10:15

*/

import it.smallcode.smallpets.SmallPets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        SmallPets.getInstance().getUserManager().loadUser(e.getPlayer().getUniqueId().toString(), SmallPets.getInstance().getPetMapManager());

        SmallPets.getInstance().getUserManager().getUser(e.getPlayer().getUniqueId().toString()).spawnSelected();

    }

}
