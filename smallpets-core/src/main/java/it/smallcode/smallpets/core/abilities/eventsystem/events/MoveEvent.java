package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
07.08.2021 20:35

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class MoveEvent extends AbilityEvent implements Cancellable {

    private Player player;

    private Location from;
    private Location to;

    private boolean cancelled = false;

    public MoveEvent(User user, Player player, Location from, Location to) {

        super(user);

        this.player = player;

        this.from = from;
        this.to = to;

    }

    public Player getPlayer() {
        return player;
    }

    public Location getTo() {
        return to;
    }

    public Location getFrom() {
        return from;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
