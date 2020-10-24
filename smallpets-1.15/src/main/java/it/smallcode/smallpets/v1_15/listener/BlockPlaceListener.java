package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
05.07.2020 19:36

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e){

        if(e.getPlayer() != null){

            ItemStack item = e.getItemInHand();

            if(SmallPetsCommons.getSmallPetsCommons().getNbtTagEditor().hasNBTTag(item, "pet"))
                e.setCancelled(true);

        }

    }

}
