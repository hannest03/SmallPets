package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
13.09.2020 17:50

*/

import it.smallcode.smallpets.core.manager.AbilityManager;
import it.smallcode.smallpets.v1_15.abilities.*;

public class AbilityManager1_15 extends AbilityManager {

    /**
     * Creates an AbilityManager
     */
    public AbilityManager1_15() {

    }

    @Override
    public void registerAbilities() {

        registerAbility("damage_ability", DamageAbility.class);
        registerAbility("health_ability", HealthAbility.class);
        registerAbility("speed_in_biome_ability", SpeedBoostInBiomeAbility.class);
        registerAbility("dont_consume_arrow_ability", DontConsumeArrowAbility.class);
        registerAbility("unbreakable_bow_ability", UnbreakableBowAbility.class);
        registerAbility("faster_mining_speed_ability", FasterMiningSpeedAbility.class);
        registerAbility("heal_while_in_water_ability", HealWhileInWaterAbility.class);

    }

}
