package it.smallcode.smallpets.v1_15.abilities;
/*

Class created by SmallCode
19.10.2020 17:08

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.EnterWaterEvent;
import it.smallcode.smallpets.core.abilities.eventsystem.events.InWaterMoveEvent;
import it.smallcode.smallpets.core.abilities.templates.SpeedBoostAbility;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * smallpets-parent
 *
 * @author SmallCode
 */

public class WaterSpeedAbility extends SpeedBoostAbility {

    public WaterSpeedAbility(double maxExtraStat, double speedBoostCap) {
        super(maxExtraStat, speedBoostCap, NumberDisplayType.TWO_DECIMAL_PLACES);
    }

    public WaterSpeedAbility(double maxExtraStat, double minExtraStat, double speedBoostCap) {
        super(maxExtraStat, minExtraStat, speedBoostCap, NumberDisplayType.TWO_DECIMAL_PLACES);
    }

    @AbilityEventHandler
    public void onWaterEnter(EnterWaterEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            

            if (SmallPetsCommons.DEBUG)
                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cDEBUG: inwater " + ability.getExtraStat(e.getUser().getSelected().getLevel()));

        }

    }

}
