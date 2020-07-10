package it.smallcode.smallpets.v1_13.listener;
/*

Class created by SmallCode
10.07.2020 15:18

*/

import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockPlaceListener implements Listener {

    private JavaPlugin plugin;

    public BlockPlaceListener(JavaPlugin plugin){

        this.plugin = plugin;

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){

        if(e.getPlayer() != null){

            ItemStack itemStack = e.getItemInHand();

            net.minecraft.server.v1_13_R2.ItemStack item = CraftItemStack.asNMSCopy(itemStack);

            if(item.getTag() != null){

                if(item.getTag().hasKey("pet")) {

                    e.setCancelled(true);

                }

            }

        }

    }

}
