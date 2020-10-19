package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
12.10.2020 10:13

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.manager.types.User;

public class QuitEvent extends AbilityEvent {

    public QuitEvent(User user) {
        super(user);
    }

}
