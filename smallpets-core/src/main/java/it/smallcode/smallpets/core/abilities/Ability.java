package it.smallcode.smallpets.core.abilities;
/*

Class created by SmallCode
13.09.2020 17:42

*/

import it.smallcode.smallpets.core.manager.AbilityManager;
import org.bukkit.Bukkit;

public abstract class Ability {

    private AbilityManager abilityManager;

    public Ability(AbilityManager abilityManager){

        this.abilityManager = abilityManager;

    }

    public abstract void registerListeners();

    protected AbilityManager getAbilityManager(){ return abilityManager; }

}
