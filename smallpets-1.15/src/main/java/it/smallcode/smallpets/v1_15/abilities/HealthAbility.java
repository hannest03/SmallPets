package it.smallcode.smallpets.v1_15.abilities;
/*

Class created by SmallCode
12.10.2020 09:22

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HealthAbility extends StatBoostAbility {

    public HealthAbility() {

        super(4, NumberDisplayType.INTEGER);

    }

    @AbilityEventHandler
    public void onLevelUp(PetLevelUpEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            int level = e.getUser().getSelected().getLevel();

            Player p = e.getUser().getSelected().getOwner();

            double maxHealth = p.getMaxHealth();

            maxHealth -= (int) getExtraStat(e.getLevelBefore());
            maxHealth += (int) getExtraStat(level);

            p.setMaxHealth(maxHealth);

            if (SmallPetsCommons.debug)
                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cDEBUG: petlevelup §7Max Health: " + maxHealth);

        }

    }

    @AbilityEventHandler
    public void onSelect(PetSelectEvent e){

        if(e.getPet().hasAbility(getID())) {

            Player p = e.getOwner();

            double maxHealth = p.getMaxHealth();

            maxHealth += (int) getExtraStat(e.getPet().getLevel());

            p.setMaxHealth(maxHealth);

            if (SmallPetsCommons.debug)
                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cDEBUG: select §7Max Health: " + maxHealth);

        }

    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){

        if(e.getPet().hasAbility(getID())) {

            Player p = e.getOwner();

            double maxHealth = p.getMaxHealth();

            maxHealth -= (int) getExtraStat(e.getPet().getLevel());

            p.setMaxHealth(maxHealth);

            if (SmallPetsCommons.debug)
                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cDEBUG: deselect §7Max Health: " + maxHealth);

        }

    }

    @AbilityEventHandler
    public void onQuit(QuitEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            Player p = e.getUser().getSelected().getOwner();

            double maxHealth = p.getMaxHealth();

            maxHealth -= (int) getExtraStat(e.getUser().getSelected().getLevel());

            p.setMaxHealth(maxHealth);

            if (SmallPetsCommons.debug)
                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cDEBUG: quit §7Max Health: " + maxHealth);

        }

    }

    @AbilityEventHandler
    public void onJoin(JoinEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            Player p = e.getUser().getSelected().getOwner();

            double maxHealth = p.getMaxHealth();

            maxHealth += (int) getExtraStat(e.getUser().getSelected().getLevel());

            p.setMaxHealth(maxHealth);

            if (SmallPetsCommons.debug)
                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cDEBUG: join §7Max Health: " + maxHealth);

        }

    }

}
