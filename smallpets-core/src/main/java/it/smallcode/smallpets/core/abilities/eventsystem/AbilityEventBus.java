package it.smallcode.smallpets.core.abilities.eventsystem;
/*

Class created by SmallCode
11.10.2020 15:39

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import org.bukkit.Bukkit;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AbilityEventBus {

    private static final Map<Class<?>, AbilityEventHandlerMethod[]> backedAbilityEventHandlers = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Map<Object, Method[]>> byListener = new ConcurrentHashMap<>();
    private static final Lock lock = new ReentrantLock();

    public static void register(Object listener){

        Map<Class<?>, List<Method>> handler = getHandlers(listener);
        lock.lock();

        try {

            for (Map.Entry<Class<?>, List<Method>> e : handler.entrySet() ) {

                Map<Object, Method[]> listenerMap = byListener.get(e.getKey());

                if(listenerMap == null){

                    listenerMap = new HashMap<>();
                    byListener.put(e.getKey(), listenerMap);

                }

                Method[] baked = new Method[e.getValue().size()];
                listenerMap.put(listener, e.getValue().toArray(baked));

                bakeHandlers(e.getKey());

            }

        } finally{

            lock.unlock();

        }

    }

    private static Map<Class<?>, List<Method>> getHandlers(Object listener) {

        Map<Class<?>, List<Method>> handler = new HashMap<>();

        for(Method method : listener.getClass().getDeclaredMethods()) {

            if(method.isAnnotationPresent(AbilityEventHandler.class)) {

                Class<?>[] params = method.getParameterTypes();

                if(params.length != 1){

                    System.out.println("SmallPets: " + "Illegal listener " + method.getName() + " method params!");

                    continue;

                }

                List<Method> methods = handler.get(params[0]);

                if(methods == null) {

                    handler.put(params[0], new LinkedList<>());

                }

                handler.get(params[0]).add(method);

            }
        }

        return handler;

    }

    private static void bakeHandlers(Class<?> eventClass) {

        Map<Object, Method[]> handlers = byListener.get(eventClass);

        if(handlers != null) {

            List<AbilityEventHandlerMethod> handlersList = new ArrayList<>();

            for(Map.Entry<Object, Method[]> listenerHandlers : handlers.entrySet()){

                for(Method method : listenerHandlers.getValue()){

                    AbilityEventHandlerMethod abilityEventHandlerMethod = new AbilityEventHandlerMethod(listenerHandlers.getKey(), method);
                    handlersList.add(abilityEventHandlerMethod);

                }

            }

            backedAbilityEventHandlers.put(eventClass, handlersList.toArray(new AbilityEventHandlerMethod[handlersList.size()]));

        }else{

            backedAbilityEventHandlers.remove(eventClass);

        }

    }

    public static void post(Object event){

        AbilityEventHandlerMethod[] handlerMethods = backedAbilityEventHandlers.get(event.getClass());

        if(handlerMethods != null){

            for(AbilityEventHandlerMethod abilityEventHandlerMethod : handlerMethods){

                try {

                    abilityEventHandlerMethod.fire(event);

                } catch (InvocationTargetException ex) {

                    ex.printStackTrace();

                } catch (IllegalAccessException ex) {

                    ex.printStackTrace();

                }

            }

        }

    }

}
