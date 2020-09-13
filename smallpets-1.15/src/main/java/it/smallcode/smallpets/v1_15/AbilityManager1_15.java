package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
13.09.2020 17:50

*/

import it.smallcode.smallpets.core.manager.AbilityManager;
import it.smallcode.smallpets.v1_15.abilities.DamageAbility;

public class AbilityManager1_15 extends AbilityManager {

    @Override
    public void registerAbilities() {

        abilityMap.put("damage_ability", DamageAbility.class);

    }

}
