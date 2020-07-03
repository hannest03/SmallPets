package it.smallcode.smallpets.events;
/*

Class created by SmallCode
03.07.2020 19:48

*/

import it.smallcode.smallpets.pets.Pet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DespawnPetEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Pet pet;
    private Player owner;

    private boolean cancelled = false;

    public DespawnPetEvent(Pet pet, Player owner) {
        this.pet = pet;
        this.owner = owner;
    }

    public Pet getPet() {
        return pet;
    }

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
