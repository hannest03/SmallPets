package it.smallcode.smallpets.v1_12.listener.experience;
/*

Class created by SmallCode
25.08.2020 22:10

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.ExperienceManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityDeathLListener implements Listener {

    private double xpMultiplier;

    public EntityDeathLListener(double xpMultiplier){

        this.xpMultiplier = xpMultiplier;

    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e){

        Player p = e.getEntity().getKiller();

        if(p != null) {

            if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard()){

                if(!WorldGuardImp.checkStateFlag(p, SmallFlags.GIVE_EXP))
                    return;

            }

            ExperienceManager experienceManager = SmallPetsCommons.getSmallPetsCommons().getExperienceManager();

            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

            if (user != null) {

                if (user.getSelected() != null) {

                    String type = e.getEntityType().name().toLowerCase();

                    if(experienceManager.getExperienceTableAll().containsKey(type)){

                        double extraMultiplier = 1;
                        if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard())
                            extraMultiplier = WorldGuardImp.getDoubleFlagValue(p, SmallFlags.EXP_MODIFIER, 1D);

                        int exp = experienceManager.getExperienceTableAll().get(type);

                        if(experienceManager.getPetTypeOfType(type) != user.getSelected().getPetType()) {

                            exp /= 2;

                        }

                        user.getSelected().giveExp((int) (exp * xpMultiplier * extraMultiplier), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                    }

                }

            }

        }

    }

}
