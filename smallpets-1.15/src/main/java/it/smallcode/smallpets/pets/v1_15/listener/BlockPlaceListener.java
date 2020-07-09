package it.smallcode.smallpets.pets.v1_15.listener;
/*

Class created by SmallCode
05.07.2020 19:36

*/

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockPlaceListener implements Listener {

    private JavaPlugin plugin;

    public BlockPlaceListener(JavaPlugin plugin){

        this.plugin = plugin;

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){

        if(e.getPlayer() != null){

            ItemStack item = e.getItemInHand();

            if(item.getItemMeta() != null){

                ItemMeta itemMeta = item.getItemMeta();

                if(itemMeta.getPersistentDataContainer() != null){

                    NamespacedKey key = new NamespacedKey(plugin, "pet");

                    if(itemMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING)){

                        e.setCancelled(true);

                    }

                }

            }

        }

    }

}
