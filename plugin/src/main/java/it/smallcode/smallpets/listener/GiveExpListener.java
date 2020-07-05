package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
05.07.2020 17:40

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class GiveExpListener implements Listener {

    @EventHandler
    public void onEntityKill(EntityDeathEvent e){

        Player p = e.getEntity().getKiller();

        if(p != null) {

            User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

            if (user != null) {

                if (user.getSelected() != null) {

                    user.getSelected().giveExp((int) (e.getDroppedExp() * 3 * SmallPets.getInstance().getXpMultiplier()), SmallPets.getInstance());

                }

            }

        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Player p = e.getPlayer();

        User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

        if(user != null){

            if(user.getSelected() != null){

                user.getSelected().giveExp((int) (e.getExpToDrop() * 3 * SmallPets.getInstance().getXpMultiplier()), SmallPets.getInstance());

            }

        }

    }

}
