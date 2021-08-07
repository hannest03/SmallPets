package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
12.10.2020 09:30

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.entity.Player;

public class PetSelectEvent extends AbilityEvent {

    private Player owner;
    private Pet pet;

    public PetSelectEvent(Pet pet, Player owner) {
        super(SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(owner.getUniqueId().toString()));
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
