package it.smallcode.smallpets.v1_15.listener.experience;
/*

Class created by SmallCode
25.08.2020 22:10

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.ExperienceManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    private double xpMultiplier;

    public EntityDeathListener(double xpMultiplier){

        this.xpMultiplier = xpMultiplier;

    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e){

        Player p = e.getEntity().getKiller();

        if(p != null) {

            UserManager userManager = SmallPetsCommons.getSmallPetsCommons().getUserManager();
            ExperienceManager experienceManager = SmallPetsCommons.getSmallPetsCommons().getExperienceManager();

            User user = userManager.getUser(p.getUniqueId().toString());

            if (user != null) {

                if (user.getSelected() != null) {

                    String type = e.getEntityType().name().toLowerCase();

                    if(experienceManager.getExperienceTableAll().containsKey(type)){

                        int exp = experienceManager.getExperienceTableAll().get(type);

                        if(experienceManager.getPetTypeOfType(type) != user.getSelected().getPetType()) {

                            exp /= 2;

                        }

                        user.getSelected().giveExp((int) (exp * xpMultiplier), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                    }

                }

            }

        }

    }

}
