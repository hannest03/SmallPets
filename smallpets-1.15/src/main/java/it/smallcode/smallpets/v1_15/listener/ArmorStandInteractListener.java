package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
04.07.2020 16:32

*/

import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.PetMapManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

import java.util.Map;

public class ArmorStandInteractListener implements Listener {

    private PetMapManager petMapManager;
    private LanguageManager languageManager;

    public ArmorStandInteractListener(PetMapManager petMapManager, LanguageManager languageManager){

        this.petMapManager = petMapManager;
        this.languageManager = languageManager;

    }

    @EventHandler
    public void onInteract(PlayerArmorStandManipulateEvent e){

        if(e.getRightClicked().getCustomName() != null){

            for(String types : petMapManager.getPetMap().keySet()){

                if(e.getRightClicked().getCustomName().endsWith(languageManager.getLanguage().getStringFormatted("pet." + types))){

                    e.setCancelled(true);

                    break;

                }

            }

        }

    }

}
