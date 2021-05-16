package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
12.10.2020 09:22

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import it.smallcode.smallpets.core.manager.types.User;
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

                    removeBoost(e.getPet().getOwner(), e.getPet().getAbility(getID()));
                    addBoost(e.getPet().getOwner(), e.getPet().getAbility(getID()));

                }

            }

        }

    }

    @AbilityEventHandler
    public void onSelect(PetSelectEvent e){

        if(e.getPet().hasAbility(getID())) {

            addBoost(e.getOwner(), e.getPet().getAbility(getID()));

        }

    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){

        if(e.getPet().hasAbility(getID())) {

            removeBoost(e.getOwner(), e.getPet().getAbility(getID()));

        }

    }

    @AbilityEventHandler
    public void onQuit(QuitEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            removeBoost(e.getUser().getSelected().getOwner(), e.getUser().getSelected().getAbility(getID()));

        }

    }

    @AbilityEventHandler
    public void onJoin(JoinEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            addBoost(e.getUser().getSelected().getOwner(), e.getUser().getSelected().getAbility(getID()));

        }

    }

    @AbilityEventHandler
    public void onShutdown(ServerShutdownEvent e){

        if(e.getUser() != null && e.getUser().getSelected() != null)
        if(e.getUser().getSelected().hasAbility(getID())) {

            removeBoost(e.getUser().getSelected().getOwner(), e.getUser().getSelected().getAbility(getID()));

        }

    }

    @Override
    public void addBoost(Player p, Ability ability) {

        StatBoostAbility statBoostAbility = (StatBoostAbility) ability;

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        if(user == null)
            return;

        SmallPetsCommons.getSmallPetsCommons().getHealthModifierUtils().addModifier(p, getID(), statBoostAbility.getExtraStat(user.getSelected().getLevel()), AttributeModifier.Operation.ADD_NUMBER);

        debug(getID() + " add modifier");

    }

    @Override
    public void removeBoost(Player p, Ability ability) {

        SmallPetsCommons.getSmallPetsCommons().getHealthModifierUtils().removeModifier(p, getID());

        debug(getID() + " remove modifier");

    }
}
