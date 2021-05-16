package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
21.10.2020 20:57

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.InWaterMoveEvent;
import it.smallcode.smallpets.core.abilities.templates.SpeedBoostAbility;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DolphinAbility extends SpeedBoostAbility {

    public DolphinAbility(){
        this(0,0);
    }

    public DolphinAbility(double maxExtraStat, double speedBoostCap) {
        super(maxExtraStat, speedBoostCap, NumberDisplayType.TWO_DECIMAL_PLACES);
    }

    public DolphinAbility(double maxExtraStat, double minExtraStat, double speedBoostCap) {
        super(maxExtraStat, minExtraStat, speedBoostCap, NumberDisplayType.TWO_DECIMAL_PLACES);
    }

    @AbilityEventHandler
    public void inWater(InWaterMoveEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            SpeedBoostAbility ability = (SpeedBoostAbility) e.getUser().getSelected().getAbility(getID());

            Vector changed = e.getTo().getDirection();

            changed.multiply(1 + ability.getExtraStat(e.getUser().getSelected().getLevel()));

            double maxSpeed = 0.3D + (ability.getSpeedBoostCap() / Pet.MAXLEVEL * e.getUser().getSelected().getLevel());

            if(Math.abs(changed.getX()) <= maxSpeed){

                double newX = maxSpeed;

                if(changed.getX() < 0)
                    newX *= -1;

                changed.setX(newX);

            }

            if(Math.abs(changed.getZ()) <= maxSpeed){

                double newZ = maxSpeed;

                if(changed.getZ() < 0)
                    newZ *= -1;

                changed.setZ(newZ);

            }

            e.getPlayer().setVelocity(changed);

            debug("dolphin multiplier " + ability.getExtraStat(e.getUser().getSelected().getLevel()));

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
