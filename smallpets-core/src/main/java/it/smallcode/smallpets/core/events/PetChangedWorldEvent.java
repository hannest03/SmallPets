package it.smallcode.smallpets.core.events;
/*

Class created by SmallCode
04.07.2020 13:48

*/

import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * This event is being called when the pet is about to be teleported to the new world
 *
 */

public class PetChangedWorldEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Pet pet;
    private Player owner;

    private boolean cancelled = false;

    /**
     *
     * Creates a pet change world event
     *
     * @param pet - the pet
     * @param owner - the owner of the pet
     */

    public PetChangedWorldEvent(Pet pet, Player owner) {
        this.pet = pet;
        this.owner = owner;
    }

    /**
     *
     * Returns the pet
     *
     * @return the pet
     */

    public Pet getPet() {
        return pet;
    }

    /**
     *
     * Returns the owner of the pet
     *
     * @return the owner
     */

    public Player getOwner() {
        return owner;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){

        return handlers;

    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {

        this.cancelled = b;

    }

}
