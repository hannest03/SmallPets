package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
02.11.2020 20:05

*/

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;

public class ShootBowEvent implements Cancellable {

    private LivingEntity attacker;
    private Projectile projectile;

    private boolean cancelled;

    public ShootBowEvent(LivingEntity attacker, Projectile projectile) {

        this.attacker = attacker;
        this.projectile = projectile;

    }

    public Entity getAttacker() {
        return attacker;
    }

    public Projectile getProjectile() {
        return projectile;
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
