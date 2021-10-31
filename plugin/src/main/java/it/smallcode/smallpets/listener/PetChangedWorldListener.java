package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
31.10.2021 18:51

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.events.PetChangedWorldEvent;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PetChangedWorldListener implements Listener {

    @EventHandler
    public void onPetChangeWorld(PetChangedWorldEvent e){
        if(SmallPetsCommons.getSmallPetsCommons().getDisabledWorlds().contains(e.getOwner().getLocation().getWorld().getName())){
            e.setCancelled(true);
            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(e.getOwner().getUniqueId().toString());
            user.getSelected().destroy();
            user.setSelected(null);
            e.getOwner().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("pets_disabled_world"));
        }
    }

}
