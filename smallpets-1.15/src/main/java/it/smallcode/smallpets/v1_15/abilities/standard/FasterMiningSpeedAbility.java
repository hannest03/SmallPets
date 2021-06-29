package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
03.11.2020 13:55

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import it.smallcode.smallpets.core.manager.types.User;
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
        if(e.getUser().getSelected() == null)
            return;
        if(e.getUser().getSelected().hasAbility(getID())) {

            removeBoost(e.getPet().getOwner(), e.getPet().getAbility(getID()));
            addBoost(e.getPet().getOwner(), e.getPet().getAbility(getID()));

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

        int potionEffectLevel = (int) statBoostAbility.getExtraStat(user.getSelected().getLevel()) -1;
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000000, potionEffectLevel, false, false);

        p.addPotionEffect(potionEffect);

        debug(getID() + " give effect " + p.hasPotionEffect(PotionEffectType.FAST_DIGGING));

    }

    @Override
    public void removeBoost(Player p, Ability ability) {

        p.removePotionEffect(PotionEffectType.FAST_DIGGING);
        debug(getID() + " remove effect " + p.hasPotionEffect(PotionEffectType.FAST_DIGGING));

    }
}
