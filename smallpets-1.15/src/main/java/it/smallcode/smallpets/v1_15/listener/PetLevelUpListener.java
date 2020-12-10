package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
05.07.2020 14:40

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.events.PetLevelUpEvent;
import it.smallcode.smallpets.core.languages.Language;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.text.CenteredText;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PetLevelUpListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLevelUP(PetLevelUpEvent e){

        Player p = e.getPet().getOwner();

        LanguageManager languageManager = SmallPetsCommons.getSmallPetsCommons().getLanguageManager();

        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

        p.sendMessage("§2----------------------------------------------------");

        p.sendMessage(CenteredText.sendCenteredMessage(languageManager.getLanguage().getStringFormatted("experienceSummary"), 154));
        p.sendMessage("");
        p.sendMessage(CenteredText.sendCenteredMessage(languageManager.getLanguage().getStringFormatted("levelupText")
                .replaceAll("%pet_level%", "" + e.getPet().getLevel()), 154));

        if(e.getPet().getLevel() != 100)
            p.sendMessage(CenteredText.sendCenteredMessage(languageManager.getLanguage().getStringFormatted("nextLevel")
                    .replaceAll("%exp_next_level%", "" + (e.getPet().getExpForNextLevel() - e.getPet().getXp() +1))
                    .replaceAll("%pet_next_level%", "" + (e.getPet().getLevel() +1)), 154));

        p.sendMessage("");

        p.sendMessage("§2----------------------------------------------------");

    }

}
