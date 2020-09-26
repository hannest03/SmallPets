package it.smallcode.smallpets.v1_15.abilities;
/*

Class created by SmallCode
13.09.2020 17:50

*/

import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.manager.AbilityManager;
import it.smallcode.smallpets.v1_15.abilities.listener.DamageAbilityListenerTest;
import org.bukkit.Bukkit;

public class DamageAbility extends Ability {

    public DamageAbility(AbilityManager abilityManager) {
        super(abilityManager);
    }

    @Override
    public void registerListeners() {

        Bukkit.getPluginManager().registerEvents(new DamageAbilityListenerTest(), getAbilityManager().getPlugin());

    }

}
