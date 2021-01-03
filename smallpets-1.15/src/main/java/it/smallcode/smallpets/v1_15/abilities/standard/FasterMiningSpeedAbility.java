package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
03.11.2020 13:55

*/

import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FasterMiningSpeedAbility extends StatBoostAbility {

    public FasterMiningSpeedAbility(){
        this(0);
    }

    public FasterMiningSpeedAbility(double maxExtraStat) {
        this(maxExtraStat, 0);
    }

    public FasterMiningSpeedAbility(double maxExtraStat, double minExtraStat) {

        super(maxExtraStat, minExtraStat, NumberDisplayType.INTEGER);
        super.setAbilityType(AbilityType.ABILITY);

    }

    @AbilityEventHandler
    public void onLevelUp(PetLevelUpEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            int level = e.getUser().getSelected().getLevel();

            Player p = e.getUser().getSelected().getOwner();

            p.removePotionEffect(PotionEffectType.FAST_DIGGING);

            PotionEffect potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000000, (int) ability.getExtraStat(level) -1, false, false);

            p.addPotionEffect(potionEffect);

            debug("levelup give effect " + p.hasPotionEffect(PotionEffectType.FAST_DIGGING));

        }

    }

    @AbilityEventHandler
    public void onSelect(PetSelectEvent e){

        if(e.getPet().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getPet().getAbility(getID());

            Player p = e.getOwner();

            PotionEffect potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000000, (int) ability.getExtraStat(e.getPet().getLevel()) -1, false, false);

            p.addPotionEffect(potionEffect);

            debug("select give effect " + p.hasPotionEffect(PotionEffectType.FAST_DIGGING));

        }

    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){

        if(e.getPet().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getPet().getAbility(getID());

            Player p = e.getOwner();

            p.removePotionEffect(PotionEffectType.FAST_DIGGING);

            debug("deselect remove effect " + p.hasPotionEffect(PotionEffectType.FAST_DIGGING));

        }

    }

    @AbilityEventHandler
    public void onQuit(QuitEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            p.removePotionEffect(PotionEffectType.FAST_DIGGING);

            debug("quit remove effect " + p.hasPotionEffect(PotionEffectType.FAST_DIGGING));

        }

    }

    @AbilityEventHandler
    public void onJoin(JoinEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            PotionEffect potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000000, (int) getExtraStat(e.getUser().getSelected().getLevel()) -1, false, false);

            p.addPotionEffect(potionEffect);

            debug("join give effect " + p.hasPotionEffect(PotionEffectType.FAST_DIGGING));

        }

    }

    @AbilityEventHandler
    public void onShutdown(ServerShutdownEvent e){

        if(e.getUser() != null && e.getUser().getSelected() != null)
        if(e.getUser().getSelected().hasAbility(getID())) {

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            Player p = e.getUser().getSelected().getOwner();

            p.removePotionEffect(PotionEffectType.FAST_DIGGING);

            debug("shutdown remove effect " + p.hasPotionEffect(PotionEffectType.FAST_DIGGING));

        }

    }

}
