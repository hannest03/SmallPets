package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
13.09.2020 17:50

*/

import it.smallcode.smallpets.core.manager.AbilityManager;
import it.smallcode.smallpets.v1_15.abilities.DamageAbility;
import it.smallcode.smallpets.v1_15.abilities.HealthAbility;
import org.bukkit.plugin.java.JavaPlugin;

public class AbilityManager1_15 extends AbilityManager {

    /**
     * Creates an AbilityManager
     *
     * @param plugin
     */
    public AbilityManager1_15() {

    }

    @Override
    public void registerAbilities() {

        registerAbility("damage_ability", DamageAbility.class);
        registerAbility("health_ability", HealthAbility.class);

    }

}
