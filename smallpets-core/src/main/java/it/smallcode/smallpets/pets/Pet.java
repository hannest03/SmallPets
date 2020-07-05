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

    private static final double minLevel = 1;
    private static final double maxLevel = 100;
    private static final double xpToLevelTwo = 1000;

    private final double tach;

    protected int xp = 0;

    protected ArmorStand armorStand;

    protected Player owner;

    public Pet(Player owner, Integer xp) {

        this.owner = owner;
        this.xp = xp;

        tach = -(Math.log(((getLevel() +1) - maxLevel) / -(maxLevel - minLevel)) / xpToLevelTwo);

    }

    public Pet(Player owner) {

        this(owner, 0);

    }

    public abstract String getName();

    public abstract void spawn(JavaPlugin plugin);

    public abstract void move();

    public abstract void idle();

    public abstract ItemStack getItem();

    public abstract void destroy();

    public int getLevel(){

        return (int) (maxLevel - (maxLevel - minLevel) * Math.pow(Math.E, -tach * xp));

    }

    public int getExpForLevel(int level){

        return (int) (Math.log(((level) - maxLevel) / -(maxLevel - minLevel)) / -tach);

    }

    public int getExpForNextLevel(){

        return getExpForLevel(getLevel()+1);

    }

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
