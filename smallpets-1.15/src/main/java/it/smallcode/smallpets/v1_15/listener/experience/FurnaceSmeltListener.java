package it.smallcode.smallpets.v1_15.listener.experience;
/*

Class created by SmallCode
26.08.2020 21:54

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.ExperienceManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

        if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard()){

            if(!WorldGuardImp.checkStateFlag(e.getPlayer(), SmallFlags.GIVE_EXP))
                return;

        }

        Player p = e.getPlayer();

        ExperienceManager experienceManager = SmallPetsCommons.getSmallPetsCommons().getExperienceManager();

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        if(user != null){

            if(user.getSelected() != null){

                String type = e.getItemType().toString().toLowerCase();

                if(experienceManager.getExperienceTableAll().containsKey(type)){

                    double extraMultiplier = 1;
                    if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard())
                        extraMultiplier = WorldGuardImp.getDoubleFlagValue(p, SmallFlags.EXP_MODIFIER, 1D);

                    int exp = experienceManager.getExperienceTableAll().get(type);

                    if(experienceManager.getPetTypeOfType(type) != user.getSelected().getPetType()) {

                        exp /= 2;

                    }

                    user.getSelected().giveExp((int) (exp * xpMultiplier * extraMultiplier) * e.getItemAmount(), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                }

            }

        }

    }

}
