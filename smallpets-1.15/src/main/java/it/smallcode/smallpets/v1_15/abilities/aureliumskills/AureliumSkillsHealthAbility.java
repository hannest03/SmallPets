package it.smallcode.smallpets.v1_15.abilities.aureliumskills;
/*

Class created by SmallCode
05.11.2020 17:02

*/

import com.archyx.aureliumskills.api.AureliumAPI;
import com.archyx.aureliumskills.stats.Stat;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
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

                    StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

                    int level = e.getUser().getSelected().getLevel();

                    Player p = e.getUser().getSelected().getOwner();

                    removeStatModifier(p);

                    int modifier = (int) ability.getExtraStat(e.getPet().getLevel());

                    addStatModifier(p, modifier);

                    debug("petlevelup add modifier " + modifier);

                }

            }

        }

    }

    @AbilityEventHandler
    public void onSelect(PetSelectEvent e){

        if(e.getPet().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getPet().getAbility(getID());

            Player p = e.getOwner();

            int modifier = (int) ability.getExtraStat(e.getPet().getLevel());

            addStatModifier(p, modifier);

            debug("select modifier: " + modifier);

        }

    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){

        if(e.getPet().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getPet().getAbility(getID());

            Player p = e.getOwner();

            removeStatModifier(p);

            debug("deselect remove modifier");

        }

    }

    @AbilityEventHandler
    public void onQuit(QuitEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            removeStatModifier(p);

            debug("quit remove modifier");
        }

    }

    @AbilityEventHandler
    public void onJoin(JoinEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            int modifier = (int) ability.getExtraStat(e.getUser().getSelected().getLevel());

            addStatModifier(p, modifier);

            debug("join add modifier: " + modifier);

        }

    }

    @AbilityEventHandler
    public void onShutdown(ServerShutdownEvent e){

        if(e.getUser() != null && e.getUser().getSelected() != null)
            if(e.getUser().getSelected().hasAbility(getID())) {

                StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

                Player p = e.getUser().getSelected().getOwner();

                removeStatModifier(p);

                debug("server shutdown remove modifier");

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

}
