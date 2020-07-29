package it.smallcode.smallpets.v1_13.listener;
/*

Class created by SmallCode
10.07.2020 15:11

*/

import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.UserManager;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class UnlockListener implements Listener {

    private JavaPlugin plugin;

    private LanguageManager languageManager;
    private UserManager userManager;
    private String prefix;

    public UnlockListener(JavaPlugin plugin, LanguageManager languageManager, UserManager userManager, String prefix){

        this.plugin = plugin;

        this.languageManager = languageManager;
        this.userManager = userManager;
        this.prefix = prefix;

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)){

            if(e.getItem() != null){

                ItemStack itemStack = e.getItem();

                net.minecraft.server.v1_13_R2.ItemStack item = CraftItemStack.asNMSCopy(itemStack);

                if(item.getTag() != null){

                    if(item.getTag().hasKey("pet")) {

                        String type = item.getTag().getString("pet");

                        if(userManager.giveUserPet(type, e.getPlayer().getUniqueId().toString())){

                            e.getItem().setAmount(e.getItem().getAmount() -1);

                            e.getPlayer().sendMessage(prefix + languageManager.getLanguage().getStringFormatted("petUnlock")
                                    .replaceAll("%pet_type%", languageManager.getLanguage().getStringFormatted("pet." + type)));

                        }

                    }

                }

            }

        }

    }

}
