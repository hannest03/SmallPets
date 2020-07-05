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
    private static final double maxLevel = 101;
    private static final double xpToLevelTwo = 500;

    private final double tach;

    protected long xp = 0;

    protected ArmorStand armorStand;

    protected Player owner;

    private static final String[] levelColors = {"§7", "§2", "§a", "§e", "§6", "§c", "§4", "§d", "§b", "§f"};

    public Pet(Player owner, Long xp) {

        this.owner = owner;
        this.xp = xp;

        tach = -(Math.log(((getLevel() +1) - maxLevel) / -(maxLevel - minLevel)) / xpToLevelTwo);

    }

    public Pet(Player owner) {

        this(owner, 0L);

    }

    public abstract void giveExp(int exp, JavaPlugin plugin);

    public abstract String getName();

    public abstract void spawn(JavaPlugin plugin);

    public abstract void move();

    public abstract void idle();

    public abstract ItemStack getItem();

    public abstract void destroy();

    public int getLevel(){

        return (int) (maxLevel - (maxLevel - minLevel) * Math.pow(Math.E, -tach * xp));

    }

    public long getExpForLevel(int level){

        return (int) (Math.log(((level) - maxLevel) / -(maxLevel - minLevel)) / -tach);

    }

    public long getExpForNextLevel(){

        return getExpForLevel(getLevel()+1);

    }

    public String getLevelColor(){

        int index = getLevel() / 10;

        if(levelColors.length <= index)
            return "";

        return levelColors[index];

    }

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
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
