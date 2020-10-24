package it.smallcode.smallpets.v1_15.abilities;
/*

Class created by SmallCode
13.09.2020 17:50

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.DamageEvent;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import org.bukkit.Bukkit;

public class DamageAbility extends StatBoostAbility {

    public DamageAbility(){

        this(0);

    }

    public DamageAbility(double maxExtraStat) {

        super(maxExtraStat, NumberDisplayType.TWO_DECIMAL_PLACES);

    }

    @AbilityEventHandler
    public void handleDamage(DamageEvent e){

        if(e.getUser().getSelected().hasAbility(getID())){

            DamageAbility ability = (DamageAbility) e.getUser().getSelected().getAbility(getID());

            double damage = e.getDamage();

            double extraDamagePercentage = ability.getExtraStat(e.getUser().getSelected().getLevel());

            double newDamage = damage + (damage * extraDamagePercentage);

            e.setDamage(newDamage);

            if(SmallPetsCommons.DEBUG)
                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cDEBUG: §7Damage Ability " + damage);

        }

    }

}
