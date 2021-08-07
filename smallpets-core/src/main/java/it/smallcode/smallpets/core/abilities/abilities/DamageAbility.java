package it.smallcode.smallpets.core.abilities.abilities;
/*

Class created by SmallCode
07.08.2021 16:13

*/

import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.DamageEvent;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import org.bukkit.entity.Player;

public class DamageAbility extends StatBoostAbility {

    @AbilityEventHandler
    public void handleDamage(DamageEvent e){
        if(e.getUser().getSelected().hasAbility(getID())){
            DamageAbility ability = (DamageAbility) e.getUser().getSelected().getAbility(getID());

            double damage = e.getDamage();
            double extraDamagePercentage = ability.getStatBoost(e.getUser().getSelected().getLevel());
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

    }
}
