package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
31.10.2021 18:51

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.events.PetChangedWorldEvent;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PetChangedWorldListener implements Listener {

    @EventHandler
    public void onPetChangeWorld(PetChangedWorldEvent e){
        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(e.getOwner().getUniqueId().toString());
        if(SmallPetsCommons.getSmallPetsCommons().getDisabledWorlds().contains(e.getOwner().getLocation().getWorld().getName())){
            e.setCancelled(true);
            user.getSelected().destroy();
            user.setSelected(null);
            e.getOwner().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("pets_disabled_world"));
        }

        if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard() && !WorldGuardImp.checkStateFlag(e.getOwner(), SmallFlags.SHOW_PETS)){
            Bukkit.getScheduler().scheduleSyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), new Runnable() {
                @Override
                public void run() {
                    user.despawnSelected();
                }
            }, 5);
        }
    }

}
