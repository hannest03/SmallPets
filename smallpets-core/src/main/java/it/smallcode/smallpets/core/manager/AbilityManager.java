package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
13.09.2020 17:43

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;

import java.util.HashMap;
import java.util.Map;

public abstract class AbilityManager {

    private final HashMap<String, Class> abilityMap;

    /**
     *
     * Creates an AbilityManager
     *
     */

    public AbilityManager() {

        abilityMap = new HashMap<>();

    }

    /**
     *
     * Registers all the abilities of a version
     *
     */

    public abstract void registerAbilities();

    public void registerAbility(String id, Class clazz){
        abilityMap.put(id, clazz);
        Ability ability = createAbility(id);
        AbilityEventBus.register(ability);
    }

    public Ability createAbility(String id){
        if(abilityMap.get(id) != null){
            try {
                return (Ability) abilityMap.get(id).newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        SmallPetsCommons.getSmallPetsCommons().getLogger().warn("Ability with id " + id + " not found!");
        return null;
    }

    public boolean hasID(String id){

        return abilityMap.get(id) != null;

    }

    public String getIDByClass(Class clazz){

        for(Map.Entry entry : abilityMap.entrySet()){

            if(entry.getValue() == clazz){

                return (String) entry.getKey();

            }

        }

        return "No ID!";

    }

}
