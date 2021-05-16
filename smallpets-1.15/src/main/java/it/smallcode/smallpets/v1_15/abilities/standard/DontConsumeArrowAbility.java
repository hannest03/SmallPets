package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
02.11.2020 19:53

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.ShootBowEvent;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.entity.Player;

import java.util.Random;

public class DontConsumeArrowAbility extends StatBoostAbility {

    public DontConsumeArrowAbility(){super(0, NumberDisplayType.TWO_DECIMAL_PLACES); }

    public DontConsumeArrowAbility(double maxExtraStat) {
        super(maxExtraStat, NumberDisplayType.TWO_DECIMAL_PLACES);
    }

    public DontConsumeArrowAbility(double maxExtraStat, double minExtraStat) {
        super(maxExtraStat, minExtraStat, NumberDisplayType.TWO_DECIMAL_PLACES);
    }

    @AbilityEventHandler
    public void onLaunchArrow(ShootBowEvent e){

        if(e.getAttacker() instanceof Player){

            Player p = (Player) e.getAttacker();

            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

            if(user != null && user.getSelected() != null && user.getSelected().hasAbility(getID())){

                StatBoostAbility ability = (StatBoostAbility) user.getSelected().getAbility(getID());

                double chance = ability.getExtraStat(user.getSelected().getLevel());

                double random = new Random().nextInt(100);

                if(random < chance){

                    e.setCancelled(true);
                    p.launchProjectile(e.getProjectile().getClass());
                    p.updateInventory();

                }

                debug("launchProjectile " + chance + " / " + random);

            }

        }

    }

    @Override
    public void addBoost(Player p, Ability ability) {

    }

    @Override
    public void removeBoost(Player p, Ability ability) {

        //Nothing needs to be changed

    }
}
