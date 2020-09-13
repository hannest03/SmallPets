package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
05.07.2020 17:40

*/

import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class GiveExpListener implements Listener {

    private JavaPlugin plugin;

    private UserManager userManager;

    private double xpMultiplier;

    public GiveExpListener(JavaPlugin plugin, UserManager userManager, double xpMultiplier){

        this.plugin = plugin;

        this.userManager = userManager;

        this.xpMultiplier = xpMultiplier;

    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e){

        Player p = e.getEntity().getKiller();

        if(p != null) {

            User user = userManager.getUser(p.getUniqueId().toString());

            if (user != null) {

                if (user.getSelected() != null) {

                    user.getSelected().giveExp((int) (e.getDroppedExp() * 3 * xpMultiplier), plugin);

                }

            }

        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Player p = e.getPlayer();

        User user = userManager.getUser(p.getUniqueId().toString());

        if(user != null){

            if(user.getSelected() != null){

                user.getSelected().giveExp((int) (e.getExpToDrop() * 3 * xpMultiplier), plugin);

            }

        }

    }

}
