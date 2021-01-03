package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
02.01.2021 23:05

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockInteractListener implements Listener {

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e){

        SmallPetsCommons.getSmallPetsCommons().getMetaDataUtils().setMetaData(e.getBlock(), "playerPlaced", e.getPlayer().getUniqueId().toString());

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreakEvent(BlockBreakEvent e){

        SmallPetsCommons.getSmallPetsCommons().getMetaDataUtils().removeMetaData(e.getBlock(), "playerPlaced");

    }

}
