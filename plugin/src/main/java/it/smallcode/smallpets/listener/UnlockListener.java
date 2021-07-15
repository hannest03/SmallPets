package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
24.10.2020 13:45

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class UnlockListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)){

            if(e.getItem() != null){

                ItemStack item = e.getItem();

                if(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(item, "pet")) {

                    String type = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().getNBTTagValue(item, "pet");

                    long exp = 0;

                    if(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(item, "petExp")){

                        try {

                            exp = Long.parseLong(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().getNBTTagValue(item, "petExp"));

                        }catch(Exception ex){ exp = 0; }

                    }

                    if(SmallPetsCommons.getSmallPetsCommons().getUserManager().giveUserPet(type, e.getPlayer().getUniqueId().toString(), exp)){

                        e.getItem().setAmount(e.getItem().getAmount() -1);

                        if(SmallPetsCommons.getSmallPetsCommons().isShowUnlockMessage()) {
                            e.getPlayer().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("petUnlock")
                                    .replaceAll("%pet_type%", SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("pet." + type)));
                        }

                    }

                }

            }

        }

    }

}
