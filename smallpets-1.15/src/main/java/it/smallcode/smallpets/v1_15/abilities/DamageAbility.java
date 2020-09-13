package it.smallcode.smallpets.v1_15.abilities;
/*

Class created by SmallCode
13.09.2020 17:50

*/

import it.smallcode.smallpets.core.manager.types.Ability;
import org.bukkit.Bukkit;

public class DamageAbility extends Ability {

    @Override
    public void registerListener() {

        Bukkit.getConsoleSender().sendMessage("§bREGISTERING DAMAGE ABILITY");

    }
}
