package it.smallcode.smallpets.core.abilities;
/*

Class created by SmallCode
13.09.2020 17:42

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Bukkit;

import java.util.List;

public abstract class Ability {

    private AbilityType abilityType;

    public Ability(){}

    public Ability(AbilityType abilityType){

        this.abilityType = abilityType;

    }

    protected void debug(String msg){

        if(SmallPetsCommons.DEBUG)
            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cDEBUG: " + getID() + "§7 " + msg);

    }

    public abstract List<String> getAbilityTooltip(Pet pet);

    public String getID(){ return SmallPetsCommons.getSmallPetsCommons().getAbilityManager().getIDByClass(this.getClass()); }

    public AbilityType getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(AbilityType abilityType) {
        this.abilityType = abilityType;
    }
}
