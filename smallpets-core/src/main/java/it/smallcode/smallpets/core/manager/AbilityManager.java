package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
13.09.2020 17:43

*/

import it.smallcode.smallpets.core.abilities.Ability;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbilityManager {

    protected HashMap<String, Class> abilityMap;

    private JavaPlugin plugin;

    /**
     *
     * Creates an AbilityManager
     *
     */

    public AbilityManager(JavaPlugin plugin) {

        abilityMap = new HashMap<String, Class>();
        this.plugin = plugin;

    }

    /**
     *
     * Registers all the abilities of a version
     *
     */

    public abstract void registerAbilities();

    public Ability createAbility(String id){

        if(abilityMap.get(id) != null){

            try {

                Constructor constructor = abilityMap.get(id).getConstructor(AbilityManager.class);

                Ability ability = (Ability) constructor.newInstance(this);

                return ability;

            } catch (InstantiationException ex) {

                ex.printStackTrace();

            } catch (IllegalAccessException ex) {

                ex.printStackTrace();

            } catch (NoSuchMethodException ex) {

                ex.printStackTrace();

            } catch (InvocationTargetException ex) {
                
                ex.printStackTrace();
                
            }

        }

        return null;

    }

    public void registerListener(){

        abilityMap.keySet().stream().forEach(id -> {

           Ability ability = createAbility(id);

           if(ability != null) {

               ability.registerListeners();

           }else{

               Bukkit.getConsoleSender().sendMessage("§cERROR REGISTERING '" + id + "' ABILITY!");

           }

        });

    }

    public String getIDByClass(Class clazz){

        for(Map.Entry entry : abilityMap.entrySet()){

            if(entry.getValue() == clazz){

                return (String) entry.getKey();

            }

        }

        return "No ID!";

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

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
