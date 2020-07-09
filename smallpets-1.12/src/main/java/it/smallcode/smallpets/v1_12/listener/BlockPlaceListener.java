package it.smallcode.smallpets.v1_12.listener;
/*

Class created by SmallCode
09.07.2020 22:06

*/

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
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

            net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);

            if(item.getTag() != null){

                if(item.getTag().hasKey("pet")) {

                    e.setCancelled(true);

                }

            }

        }

    }

}
