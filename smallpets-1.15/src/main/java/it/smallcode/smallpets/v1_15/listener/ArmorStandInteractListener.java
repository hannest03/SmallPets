package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
04.07.2020 16:32

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.PetMapManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class ArmorStandInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerArmorStandManipulateEvent e){

        if(e.getRightClicked().getCustomName() != null){

            PetMapManager petMapManager = SmallPetsCommons.getSmallPetsCommons().getPetMapManager();
            LanguageManager languageManager = SmallPetsCommons.getSmallPetsCommons().getLanguageManager();

            for(String types : petMapManager.getPetMap().keySet()){

                if(e.getRightClicked().getCustomName().endsWith(languageManager.getLanguage().getStringFormatted("pet." + types))){

                    e.setCancelled(true);

                    break;

                }

            }

        }

    }

}
