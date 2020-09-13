package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
13.09.2020 17:43

*/

import it.smallcode.smallpets.core.manager.types.Ability;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public abstract class AbilityManager {

    protected HashMap<String, Class> abilityMap;

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

    public void registerListener(){

        abilityMap.values().stream().forEach(abilityClass -> {

            if(abilityClass != null){

                try {

                    Ability ability = (Ability) abilityClass.newInstance();

                    ability.registerListener();

                } catch (InstantiationException ex) {

                    ex.printStackTrace();

                } catch (IllegalAccessException ex) {

                    ex.printStackTrace();

                }

            }else{

                Bukkit.getConsoleSender().sendMessage("§4 ERROR REGISTERING ABILITY!");

            }

        });

    }

    /**
     *
     * Returns the abilityMap
     *
     * @return the abilityMap
     */

    public HashMap<String, Class> getAbilityMap() {
        return abilityMap;
    }

}
