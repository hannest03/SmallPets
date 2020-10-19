package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
12.10.2020 09:30

*/

import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.entity.Player;

public class PetSelectEvent {

    private Player owner;
    private Pet pet;

    public PetSelectEvent(Pet pet, Player owner) {

        this.owner = owner;
        this.pet = pet;

    }

    public Player getOwner() {
        return owner;
    }

    public Pet getPet() {
        return pet;
    }

}
