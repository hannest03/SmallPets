package it.smallcode.smallpets.v1_15.abilities;
/*

Class created by SmallCode
13.09.2020 17:50

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import it.smallcode.smallpets.v1_15.abilities.listener.DamageAbilityListener;
import org.bukkit.Bukkit;

public class DamageAbility extends StatBoostAbility {

    public DamageAbility() {

        super(15);

    }

    @Override
    public void registerListeners() {

        Bukkit.getPluginManager().registerEvents(new DamageAbilityListener(this), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

    }
}
