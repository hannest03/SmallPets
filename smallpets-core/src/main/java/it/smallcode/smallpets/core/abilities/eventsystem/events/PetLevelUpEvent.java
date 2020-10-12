package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
12.10.2020 09:24

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.manager.types.User;

public class PetLevelUpEvent extends AbilityEvent {

    private int levelBefore;

    public PetLevelUpEvent(User user, int levelBefore) {

        super(user);

        this.levelBefore = levelBefore;

    }

    public int getLevelBefore() {
        return levelBefore;
    }
}
