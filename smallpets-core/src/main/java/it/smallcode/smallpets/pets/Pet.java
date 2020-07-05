package it.smallcode.smallpets.pets;
/*

Class created by SmallCode
02.07.2020 14:43

*/

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * The basic pet class
 *
 */

public abstract class Pet {

    private static final double minLevel = 1;
    private static final double maxLevel = 101;
    private static final double xpToLevelTwo = 500;

    private final double tach;

    protected long xp = 0;

    protected ArmorStand armorStand;

    protected Player owner;

    private static final String[] levelColors = {"§7", "§2", "§a", "§e", "§6", "§c", "§4", "§d", "§b", "§f"};

    /**
     *
     * Creates a pet
     *
     * @param owner - the pet owner
     * @param xp - the xp
     */

    public Pet(Player owner, Long xp) {

        this.owner = owner;
        this.xp = xp;

        tach = -(Math.log(((getLevel() +1) - maxLevel) / -(maxLevel - minLevel)) / xpToLevelTwo);

    }

    /**
     *
     * Creates a pet
     *
     * @param owner - the owner
     */

    public Pet(Player owner) {

        this(owner, 0L);

    }

    /**
     *
     * Gives exp to the pet
     *
     * @param exp - the amount of exp
     * @param plugin - the plugin
     */

    public abstract void giveExp(int exp, JavaPlugin plugin);

    /**
     *
     * Returns the type / name of the pet
     *
     * @return the type / name
     */

    public abstract String getName();

    /**
     *
     * Spawns the pet
     *
     * @param plugin - the plugin
     */

    public abstract void spawn(JavaPlugin plugin);

    /**
     *
     * Lets the pet move
     *
     */

    public abstract void move();

    /**
     *
     * Lets the pet idle
     *
     */

    public abstract void idle();

    /**
     *
     * Despawns the pet
     *
     */

    public abstract void destroy();

    /**
     *
     * Registers the crafting recipe
     *
     */

    /**
     *
     * Returns the pet head
     *
     * @return the head
     */

    public abstract ItemStack getItem();

    public abstract void registerRecipe(Plugin plugin);

    /**
     *
     * Returns the item to unlock the tiger
     *
     * @param plugin - the plugin
     * @return the item to unlock the tiger
     */

    public abstract ItemStack getUnlockItem(Plugin plugin);

    /**
     *
     * Returns the level of the pet
     *
     * @return the level
     */

    public int getLevel(){

        return (int) (maxLevel - (maxLevel - minLevel) * Math.pow(Math.E, -tach * xp));

    }

    /**
     *
     * Returns the amount of exp needed for a level
     *
     * @param level - the level
     * @return the amount of exp needed
     */

    public long getExpForLevel(int level){

        return (int) (Math.log(((level) - maxLevel) / -(maxLevel - minLevel)) / -tach);

    }

    /**
     *
     * Returns the amount of exp needed for the next level
     *
     * @return the amount of exp needed
     */

    public long getExpForNextLevel(){

        return getExpForLevel(getLevel()+1);

    }

    /**
     *
     * Returns the color of the pet
     *
     * @return the color
     */

    public String getLevelColor(){

        int index = getLevel() / 10;

        if(levelColors.length <= index)
            return "";

        return levelColors[index];

    }

    /**
     *
     * Returns the exp of the pet
     *
     * @return the exp
     */

    public long getXp() {
        return xp;
    }

    /**
     *
     * Sets the exp of the pet
     *
     * @param xp - the new exp
     */

    public void setXp(long xp) {
        this.xp = xp;
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

    /**
     *
     * Sets the owner of the pet
     *
     * @param owner - the new owner
     */

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     *
     * Returns the armorstand of the pet
     *
     * @return the armorstand
     */

    public ArmorStand getArmorStand() {
        return armorStand;
    }

}
