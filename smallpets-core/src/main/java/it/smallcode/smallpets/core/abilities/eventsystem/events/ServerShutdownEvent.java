package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
12.10.2020 11:05

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.manager.types.User;

public class ServerShutdownEvent extends AbilityEvent {

    public ServerShutdownEvent(User user) {
        super(user);
    }
}
