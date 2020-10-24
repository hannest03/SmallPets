package it.smallcode.smallpets.v1_15.listener.experience;
/*

Class created by SmallCode
26.08.2020 21:54

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.ExperienceManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FurnaceSmeltListener implements Listener {

    private double xpMultiplier;

    public FurnaceSmeltListener(double xpMultiplier){

        this.xpMultiplier = xpMultiplier;

    }

    @EventHandler
    public void onSmelt(FurnaceExtractEvent e){

        Player p = e.getPlayer();

        ExperienceManager experienceManager = SmallPetsCommons.getSmallPetsCommons().getExperienceManager();

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        if(user != null){

            if(user.getSelected() != null){

                String type = e.getItemType().toString().toLowerCase();

                if(experienceManager.getExperienceTableAll().containsKey(type)){

                    int exp = experienceManager.getExperienceTableAll().get(type);

                    if(experienceManager.getPetTypeOfType(type) != user.getSelected().getPetType()) {

                        exp /= 2;

                    }

                    user.getSelected().giveExp((int) (exp * xpMultiplier) * e.getItemAmount(), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                }

            }

        }

    }

}
