package it.smallcode.smallpets.pets.v1_15.listener;
/*

Class created by SmallCode
05.07.2020 14:40

*/

import it.smallcode.smallpets.events.PetLevelUpEvent;
import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.text.CenteredText;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PetLevelUpListener implements Listener {

    @EventHandler
    public void onLevelUP(PetLevelUpEvent e){

        Player p = e.getPet().getOwner();

        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

        p.sendMessage("§2----------------------------------------------------");

        p.sendMessage(CenteredText.sendCenteredMessage("§fExperience Summary", 154));
        p.sendMessage("");
        p.sendMessage(CenteredText.sendCenteredMessage("§eYour pet is now level §6§l" + e.getPet().getLevel() + "§e!", 154));

        if(e.getPet().getLevel() != 100)
            p.sendMessage(CenteredText.sendCenteredMessage("§eYour pet needs §6" + (e.getPet().getExpForNextLevel() - e.getPet().getXp() +1) + "§6 xp §e to reach level §6" + (e.getPet().getLevel() +1) + "§e", 154));

        p.sendMessage("");

        p.sendMessage("§2----------------------------------------------------");

    }

}
