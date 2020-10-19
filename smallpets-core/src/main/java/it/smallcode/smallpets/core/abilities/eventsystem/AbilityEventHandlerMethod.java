package it.smallcode.smallpets.core.abilities.eventsystem;
/*

Class created by SmallCode
11.10.2020 15:37

*/

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AbilityEventHandlerMethod {

    private final Object listener;
    private final Method method;

    public AbilityEventHandlerMethod(Object listener, Method method) {
        this.listener = listener;
        this.method = method;
    }

    public void fire(Object event) throws InvocationTargetException, IllegalAccessException {

        method.invoke(listener, event);

    }

    public Object getListener() {
        return listener;
    }

    public Method getMethod() {
        return method;
    }
}
