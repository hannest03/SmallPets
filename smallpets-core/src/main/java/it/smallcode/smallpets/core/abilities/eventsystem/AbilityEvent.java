package it.smallcode.smallpets.core.abilities.eventsystem;
/*

Class created by SmallCode
11.10.2020 19:08

*/

import it.smallcode.smallpets.core.manager.types.User;

public abstract class AbilityEvent {

    private User user;

    public AbilityEvent(User user){

        this.user = user;

    }

    public User getUser(){

        return user;

    }

}
