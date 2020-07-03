package it.smallcode.smallpets.pets;
/*

Class created by SmallCode
02.07.2020 14:43

*/

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Pet {

    protected int xp = 0;

    protected ArmorStand armorStand;

    protected Player owner;

    public Pet(Player owner, Integer xp) {

        this.owner = owner;
        this.xp = xp;

    }

    public Pet(Player owner) {

        this(owner, 0);

    }

    public abstract void spawn(JavaPlugin plugin);

    public abstract void move();

    public abstract void idle();

    public abstract ItemStack getItem();

    public abstract void destroy();

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }
}
