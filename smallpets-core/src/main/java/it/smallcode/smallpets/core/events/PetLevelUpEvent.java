package it.smallcode.smallpets.core.events;
/*

Class created by SmallCode
05.07.2020 14:37

*/

import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PetLevelUpEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Pet pet;

    public PetLevelUpEvent(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){

        return handlers;

    }


}
