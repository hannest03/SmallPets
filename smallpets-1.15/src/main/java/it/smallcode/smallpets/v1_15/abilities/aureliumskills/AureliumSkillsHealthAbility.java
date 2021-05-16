package it.smallcode.smallpets.v1_15.abilities.aureliumskills;
/*

Class created by SmallCode
05.11.2020 17:02

*/

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.stats.Stat;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AureliumSkillsHealthAbility extends StatBoostAbility {

    public AureliumSkillsHealthAbility(){
        this(0);
    }

    public AureliumSkillsHealthAbility(double maxExtraStat) {
        this(maxExtraStat, 0);
    }

    public AureliumSkillsHealthAbility(double maxExtraStat, double minExtraStat) {
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

    public void addStatModifier(Player p, int value){

        if(!AureliumAPI.addStatModifier(p, getID(), Stat.HEALTH, value)){

            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cERROR AureliumSkills add Health Modifier");

        }

    }

    public void removeStatModifier(Player p){

        if(!AureliumAPI.removeStatModifier(p, getID())){

            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cERROR AureliumSkills remove Health Modifier");

        }

    }

    @Override
    public void addBoost(Player p, Ability ability) {

        StatBoostAbility statBoostAbility = (StatBoostAbility) ability;

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        int modifier = (int) statBoostAbility.getExtraStat(user.getSelected().getLevel());

        addStatModifier(p, modifier);

        debug(getID() + " add modifier: " + modifier);

    }

    @Override
    public void removeBoost(Player p, Ability ability) {

        removeStatModifier(p);

        debug(getID() + " remove modifier");

    }
}
