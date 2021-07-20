package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
04.07.2020 16:32

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.PetManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class ArmorStandInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerArmorStandManipulateEvent e){

        if(e.getRightClicked().getCustomName() != null){

            PetManager petManager = SmallPetsCommons.getSmallPetsCommons().getPetManager();
            LanguageManager languageManager = SmallPetsCommons.getSmallPetsCommons().getLanguageManager();

            for(PetManager.NamespaceKey key : petManager.getPetMap().keySet()){

                if(e.getRightClicked().getCustomName().endsWith(languageManager.getLanguage().getStringFormatted("pet." + key.getNamespace() + "." + key.getId()))){

                    e.setCancelled(true);

                    break;

                }

            }

        }

    }

}
