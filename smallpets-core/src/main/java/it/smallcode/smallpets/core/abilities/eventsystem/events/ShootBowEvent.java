package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
02.11.2020 20:05

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public class ShootBowEvent extends AbilityEvent implements Cancellable {

    private User user;

    private LivingEntity attacker;
    private Projectile projectile;

    private ItemStack bow;

    private boolean cancelled;

    public ShootBowEvent(User user, LivingEntity attacker, Projectile projectile, ItemStack bow) {

        super(user);

        this.attacker = attacker;
        this.projectile = projectile;

        this.bow = bow;

    }

    public Entity getAttacker() {
        return attacker;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public ItemStack getBow() {
        return bow;
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
