package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
13.09.2020 17:50

*/

import it.smallcode.smallpets.core.abilities.abilities.DamageAbility;
import it.smallcode.smallpets.core.abilities.abilities.FlameCircleAbility;
import it.smallcode.smallpets.core.abilities.abilities.HealthAbility;
import it.smallcode.smallpets.core.manager.AbilityManager;
import it.smallcode.smallpets.v1_15.abilities.aureliumskills.AureliumSkillsHealthAbility;
import it.smallcode.smallpets.v1_15.abilities.standard.*;
import org.bukkit.Bukkit;

public class AbilityManager1_15 extends AbilityManager {

    @Override
    public void registerAbilities() {

        registerAbility("damage_ability", DamageAbility.class);
        registerAbility("health_ability", HealthAbility.class);
        registerAbility("speed_in_biome_ability", SpeedBoostInBiomeAbility.class);
        registerAbility("dont_consume_arrow_ability", DontConsumeArrowAbility.class);
        registerAbility("unbreakable_bow_ability", UnbreakableBowAbility.class);
        registerAbility("faster_mining_speed_ability", FasterMiningSpeedAbility.class);
        registerAbility("heal_while_in_water_ability", HealWhileInWaterAbility.class);

        registerAbility("flame_circle_ability", FlameCircleAbility.class);

        if(Bukkit.getPluginManager().getPlugin("AureliumSkills") != null && Bukkit.getPluginManager().getPlugin("AureliumSkills").isEnabled()){

            registerAbility("aurelium_skills_health_ability", AureliumSkillsHealthAbility.class);

        }

    }

}
