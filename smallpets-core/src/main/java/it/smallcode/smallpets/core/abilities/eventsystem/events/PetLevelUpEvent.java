package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
12.10.2020 09:24

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;

public class PetLevelUpEvent extends AbilityEvent {

    private Pet pet;

    private int levelBefore;

    public PetLevelUpEvent(User user, Pet pet, int levelBefore) {

        super(user);

        this.pet = pet;
        this.levelBefore = levelBefore;

    }

    public int getLevelBefore() {
        return levelBefore;
    }

    public Pet getPet() {
        return pet;
    }
}
