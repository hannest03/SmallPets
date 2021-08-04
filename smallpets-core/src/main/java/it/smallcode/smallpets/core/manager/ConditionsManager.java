package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
02.08.2021 21:42

*/

import it.smallcode.smallpets.core.conditions.Condition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ConditionsManager {

    private Map<String, Class> conditionHashMap = new HashMap<>();

    public void addCondition(String id, Class clazz){
        conditionHashMap.put(id, clazz);
    }

    public Condition getCondition(String id){
        Class<Condition> clazz = conditionHashMap.get(id);
        try {
            Constructor constructor = clazz.getConstructor();
            Condition condition = (Condition) constructor.newInstance();
            return condition;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
