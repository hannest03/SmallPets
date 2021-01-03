package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
12.10.2020 09:22

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

public class HealthAbility extends StatBoostAbility {

    public HealthAbility(){
        this(0);
    }

    public HealthAbility(double maxExtraStat) {
        this(maxExtraStat, 0);
    }

    public HealthAbility(double maxExtraStat, double minExtraStat) {
        super(maxExtraStat, minExtraStat, NumberDisplayType.INTEGER);
    }

    @AbilityEventHandler
    public void onLevelUp(PetLevelUpEvent e){

        if(e.getUser().getSelected() != null) {

            if (e.getPet().getID().equalsIgnoreCase(e.getUser().getSelected().getID())) {

                if (e.getUser().getSelected().hasAbility(getID())) {

                    StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

                    int level = e.getUser().getSelected().getLevel();

                    Player p = e.getUser().getSelected().getOwner();

                    SmallPetsCommons.getSmallPetsCommons().getHealthModifierUtils().removeModifier(p, getID());
                    SmallPetsCommons.getSmallPetsCommons().getHealthModifierUtils().addModifier(p, getID(), ability.getExtraStat(level), AttributeModifier.Operation.ADD_NUMBER);

                    debug("petlevelup modifier ");

                }

            }

        }

    }

    @AbilityEventHandler
    public void onSelect(PetSelectEvent e){

        if(e.getPet().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getPet().getAbility(getID());

            Player p = e.getOwner();

            SmallPetsCommons.getSmallPetsCommons().getHealthModifierUtils().addModifier(p, getID(), ability.getExtraStat(e.getPet().getLevel()), AttributeModifier.Operation.ADD_NUMBER);

            debug("select added modifier ");

        }

    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){

        if(e.getPet().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getPet().getAbility(getID());

            Player p = e.getOwner();

            SmallPetsCommons.getSmallPetsCommons().getHealthModifierUtils().removeModifier(p, getID());

            debug("deselect modifier");

        }

    }

    @AbilityEventHandler
    public void onQuit(QuitEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            SmallPetsCommons.getSmallPetsCommons().getHealthModifierUtils().removeModifier(p, getID());

            debug("quit modifier");
        }

    }

    @AbilityEventHandler
    public void onJoin(JoinEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            SmallPetsCommons.getSmallPetsCommons().getHealthModifierUtils().addModifier(p, getID(), ability.getExtraStat(e.getUser().getSelected().getLevel()), AttributeModifier.Operation.ADD_NUMBER);

            debug("join modifier");

        }

    }

    @AbilityEventHandler
    public void onShutdown(ServerShutdownEvent e){

        if(e.getUser() != null && e.getUser().getSelected() != null)
        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            SmallPetsCommons.getSmallPetsCommons().getHealthModifierUtils().removeModifier(p, getID());

            debug("server shutdown modifier");

        }

    }

}
