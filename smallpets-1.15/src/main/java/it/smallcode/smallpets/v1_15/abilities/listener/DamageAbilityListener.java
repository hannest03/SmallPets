package it.smallcode.smallpets.v1_15.abilities.listener;
/*

Class created by SmallCode
26.09.2020 17:55

*/

import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageAbilityListener implements Listener {

    private StatBoostAbility ability;

    public DamageAbilityListener(StatBoostAbility ability){

        this.ability = ability;

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){

        if(e.getDamager() instanceof Player){

            Player p = (Player) e.getDamager();

            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

            if(user != null){

                if(user.getSelected() != null){

                    if(user.getSelected().hasAbility(ability.getID())){

                        double damage = e.getDamage();

                        double extraDamagePercentage = ability.getExtraStat(user.getSelected().getLevel());

                        double newDamage = damage + (damage / 100 * extraDamagePercentage);

                        e.setDamage(newDamage);

                    }

                }

            }

        }

    }

}
