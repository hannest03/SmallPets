package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
03.11.2020 15:02

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class HealWhileInWaterAbility extends StatBoostAbility {

    public HealWhileInWaterAbility(){
        this(0);
    }

    public HealWhileInWaterAbility(double maxExtraStat) {
        this(maxExtraStat, 0);
    }

    public HealWhileInWaterAbility(double maxExtraStat, double minExtraStat) {

        super(maxExtraStat, minExtraStat, NumberDisplayType.INTEGER);
        super.setAbilityType(AbilityType.ABILITY);

    }

    @AbilityEventHandler
    public void onEnterWater(EnterWaterEvent e){

        if(e.getUser().getSelected().hasAbility(getID())){

            addBoost(e.getPlayer(), e.getUser().getSelected().getAbility(getID()));

        }

    }

    @AbilityEventHandler
    public void onExitWater(ExitWaterEvent e){

        if(e.getUser().getSelected().hasAbility(getID())){

            removeBoost(e.getPlayer(), e.getUser().getSelected().getAbility(getID()));

        }

    }

    @AbilityEventHandler
    public void onServerShutdown(ServerShutdownEvent e){

        if(e.getUser() != null && e.getUser().getSelected() != null)
            if(e.getUser().getSelected().hasAbility(getID())){

                Player p = Bukkit.getPlayer(UUID.fromString(e.getUser().getUuid()));

                if(p != null && p.isOnline()){

                    if(p.getLocation().getBlock().isLiquid()){

                        removeBoost(e.getUser().getSelected().getOwner(), e.getUser().getSelected().getAbility(getID()));

                    }

                }

            }

    }

    @AbilityEventHandler
    public void onQuit(QuitEvent e){

        if(e.getUser().getSelected().hasAbility(getID())){

            Player p = Bukkit.getPlayer(UUID.fromString(e.getUser().getUuid()));

            if(p != null && p.isOnline()){

                if(p.getLocation().getBlock().isLiquid()){

                    removeBoost(e.getUser().getSelected().getOwner(), e.getUser().getSelected().getAbility(getID()));

                }

            }

        }

    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){

        if(e.getPet().hasAbility(getID())){

            if(e.getOwner().getLocation().getBlock().isLiquid()){

                removeBoost(e.getOwner(), e.getPet().getAbility(getID()));

            }

        }

    }

    @Override
    public void addBoost(Player p, Ability ability) {

        StatBoostAbility statBoostAbility = (StatBoostAbility) ability;

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        if(user == null)
            return;

        PotionEffect potionEffect = new PotionEffect(PotionEffectType.REGENERATION, 1000000, (int) statBoostAbility.getExtraStat(user.getSelected().getLevel()), false, false);

        p.addPotionEffect(potionEffect);

    }

    @Override
    public void removeBoost(Player p, Ability ability) {

        p.removePotionEffect(PotionEffectType.REGENERATION);

        debug(getID() + " remove effect " + p.hasPotionEffect(PotionEffectType.REGENERATION));


    }
}
