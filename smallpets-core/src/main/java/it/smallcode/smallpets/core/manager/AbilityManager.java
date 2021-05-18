package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
13.09.2020 17:43

*/

import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;

import java.util.HashMap;
import java.util.Map;

public abstract class AbilityManager {

    private HashMap<String, Class> abilityMap;

    /**
     *
     * Creates an AbilityManager
     *
     */

    public AbilityManager() {

        abilityMap = new HashMap<String, Class>();

    }

    /**
     *
     * Registers all the abilities of a version
     *
     */

    public abstract void registerAbilities();

    public void registerAbility(String id, Class clazz){

        abilityMap.put(id, clazz);

        createAbility(id);

    }

    public Ability createAbility(String id){

        if(abilityMap.get(id) != null){

            try {

                /*

                Constructor constructor = abilityMap.get(id).getConstructor();

                Ability ability = (Ability) constructor.newInstance();

                */

                Ability ability = (Ability) abilityMap.get(id).newInstance();

                AbilityEventBus.register(ability);

                return ability;

            } catch (InstantiationException ex) {

                ex.printStackTrace();

            } catch (IllegalAccessException ex) {

                ex.printStackTrace();

            }

        }

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
