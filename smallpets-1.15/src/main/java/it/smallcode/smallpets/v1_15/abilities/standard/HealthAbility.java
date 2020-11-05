package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
12.10.2020 09:22

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
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

                    double maxHealth = p.getMaxHealth();

                    maxHealth -= (int) ability.getExtraStat(e.getLevelBefore());
                    maxHealth += (int) ability.getExtraStat(level);

                    p.setMaxHealth(maxHealth);

                    debug("petlevelup Max Health: " + maxHealth);

                }

            }

        }

    }

    @AbilityEventHandler
    public void onSelect(PetSelectEvent e){

        if(e.getPet().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getPet().getAbility(getID());

            Player p = e.getOwner();

            double maxHealth = p.getMaxHealth();

            maxHealth += (int) ability.getExtraStat(e.getPet().getLevel());

            p.setMaxHealth(maxHealth);

            debug("select Max Health: " + maxHealth);

        }

    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){

        if(e.getPet().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getPet().getAbility(getID());

            Player p = e.getOwner();

            double maxHealth = p.getMaxHealth();

            maxHealth -= (int) ability.getExtraStat(e.getPet().getLevel());

            p.setMaxHealth(maxHealth);

            debug("deselect Max Health: " + maxHealth);

        }

    }

    @AbilityEventHandler
    public void onQuit(QuitEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            double maxHealth = p.getMaxHealth();

            maxHealth -= (int) ability.getExtraStat(e.getUser().getSelected().getLevel());

            p.setMaxHealth(maxHealth);

            debug("quit Max Health: " + maxHealth);
        }

    }

    @AbilityEventHandler
    public void onJoin(JoinEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            double maxHealth = p.getMaxHealth();

            maxHealth += (int) ability.getExtraStat(e.getUser().getSelected().getLevel());

            p.setMaxHealth(maxHealth);

            debug("join §7Max Health: " + maxHealth);

        }

    }

    @AbilityEventHandler
    public void onShutdown(ServerShutdownEvent e){

        if(e.getUser() != null && e.getUser().getSelected() != null)
        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            double maxHealth = p.getMaxHealth();

            maxHealth -= (int) ability.getExtraStat(e.getUser().getSelected().getLevel());

            p.setMaxHealth(maxHealth);

            debug("server shutdown §7Max Health: " + maxHealth);

        }

    }

}
