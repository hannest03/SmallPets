package it.smallcode.smallpets.core.abilities.eventsystem;
/*

Class created by SmallCode
11.10.2020 15:37

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class AbilityEventHandlerMethod {

    private final Object listener;
    private final Method method;

    public AbilityEventHandlerMethod(Object listener, Method method) {
        this.listener = listener;
        this.method = method;
    }

    public void fire(Object event) throws InvocationTargetException, IllegalAccessException {

        if(event instanceof AbilityEvent) {

            AbilityEvent abilityEvent = (AbilityEvent) event;

            Player p = Bukkit.getPlayer(UUID.fromString(abilityEvent.getUser().getUuid()));

            if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard())
                if(!WorldGuardImp.checkStateFlag(p, SmallFlags.ALLOW_ABILITIES))
                    return;

            method.invoke(listener, event);

        }

    }

    public Object getListener() {
        return listener;
    }

    public Method getMethod() {
        return method;
    }
}
