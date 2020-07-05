package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
05.07.2020 18:59

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.manager.UserManager;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class UnlockListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)){

            if(e.getItem() != null){

                ItemStack item = e.getItem();

                if(item.getItemMeta() != null){

                    ItemMeta itemMeta = item.getItemMeta();

                    if(itemMeta.getPersistentDataContainer() != null){

                        NamespacedKey key = new NamespacedKey(SmallPets.getInstance(), "pet");

                        if(itemMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING)){

                            String type = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);

                            if(SmallPets.getInstance().getUserManager().giveUserPet(type, e.getPlayer().getUniqueId().toString())){

                                e.getItem().setAmount(e.getItem().getAmount() -1);

                                e.getPlayer().sendMessage(SmallPets.getInstance().PREFIX + "Unlocked " + type + " pet!");

                            }

                        }

                    }

                }

            }

        }

    }

}
