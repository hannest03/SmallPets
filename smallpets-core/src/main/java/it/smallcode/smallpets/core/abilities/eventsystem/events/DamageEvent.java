package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
11.10.2020 19:20

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;

public class DamageEvent extends AbilityEvent implements Cancellable {

    private Entity entity;
    private double damage;
    private boolean cancelled = false;

    public DamageEvent(User user, Entity entity, double damage) {

        super(user);

        this.entity = entity;
        this.damage = damage;

    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
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
