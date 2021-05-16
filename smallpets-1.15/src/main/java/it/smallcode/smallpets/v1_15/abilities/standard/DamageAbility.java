package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
13.09.2020 17:50

*/

import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.DamageEvent;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import org.bukkit.entity.Player;

public class DamageAbility extends StatBoostAbility {

    public DamageAbility(){
        this(0);
    }

    public DamageAbility(double maxExtraStat) {
        this(maxExtraStat, 0);
    }

    public DamageAbility(double maxExtraStat, double minExtraStat) {
        super(maxExtraStat, minExtraStat, NumberDisplayType.TWO_DECIMAL_PLACES);
    }

    @AbilityEventHandler
    public void handleDamage(DamageEvent e){

        if(e.getUser().getSelected().hasAbility(getID())){

            DamageAbility ability = (DamageAbility) e.getUser().getSelected().getAbility(getID());

            double damage = e.getDamage();

            double extraDamagePercentage = ability.getExtraStat(e.getUser().getSelected().getLevel());

            double newDamage = damage + (damage * (extraDamagePercentage / 100D));

            e.setDamage(newDamage);

            debug("Damage Ability " + e.getDamage());

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
