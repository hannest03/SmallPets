package it.smallcode.smallpets.v1_15.abilities;
/*

Class created by SmallCode
03.11.2020 15:02

*/

import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
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

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            PotionEffect potionEffect = new PotionEffect(PotionEffectType.REGENERATION, 1000000, (int) ability.getExtraStat(e.getUser().getSelected().getLevel()), false, false);

            e.getPlayer().addPotionEffect(potionEffect);

            debug("enter water add effect " + e.getPlayer().hasPotionEffect(PotionEffectType.REGENERATION));

        }

    }

    @AbilityEventHandler
    public void onExitWater(ExitWaterEvent e){

        if(e.getUser().getSelected().hasAbility(getID())){

            StatBoostAbility ability = (StatBoostAbility) e.getUser().getSelected().getAbility(getID());

            e.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);

            debug("exit water remove effect " + e.getPlayer().hasPotionEffect(PotionEffectType.REGENERATION));

        }

    }

    @AbilityEventHandler
    public void onServerShutdown(ServerShutdownEvent e){

        if(e.getUser() != null && e.getUser().getSelected() != null)
            if(e.getUser().getSelected().hasAbility(getID())){

                Player p = Bukkit.getPlayer(UUID.fromString(e.getUser().getUuid()));

                if(p != null && p.isOnline()){

                    if(p.getLocation().getBlock().isLiquid()){

                        p.removePotionEffect(PotionEffectType.REGENERATION);

                        debug("shutdown remove effect " + p.hasPotionEffect(PotionEffectType.REGENERATION));

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

                    p.removePotionEffect(PotionEffectType.REGENERATION);

                    debug("quit remove effect " + p.hasPotionEffect(PotionEffectType.REGENERATION));

                }

            }

        }

    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){

        if(e.getPet().hasAbility(getID())){

            if(e.getOwner().getLocation().getBlock().isLiquid()){

                e.getOwner().removePotionEffect(PotionEffectType.REGENERATION);

                debug("deselect remove effect " + e.getOwner().hasPotionEffect(PotionEffectType.REGENERATION));

            }

        }

    }

}
