package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
12.10.2020 09:31

*/

import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.entity.Player;

public class PetDeselectEvent {

    private Player owner;
    private Pet pet;

    public PetDeselectEvent(Pet pet, Player owner) {

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
