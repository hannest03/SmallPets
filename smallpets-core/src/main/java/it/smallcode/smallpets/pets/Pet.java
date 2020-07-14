package it.smallcode.smallpets.pets;
/*

Class created by SmallCode
02.07.2020 14:43

*/

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;

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

    protected boolean useProtocolLib = false;

    protected static List<Integer> entityIDs = new LinkedList<>();

    protected ArmorStand armorStand;

    protected Location location;
    protected int entityID;

    protected Player owner;

    private boolean pauseLogic = false;

    private boolean activated;

    private static final String[] levelColors = {"§7", "§2", "§a", "§e", "§6", "§c", "§4", "§d", "§b", "§f"};

    /**
     *
     * Creates a pet
     *
     * @param owner - the pet owner
     * @param xp - the xp
     * @param useProtocolLib - boolean if protocolLib is being used
     */

    public Pet(Player owner, Long xp, Boolean useProtocolLib) {

        this.owner = owner;
        this.xp = xp;

        this.useProtocolLib = useProtocolLib;

        tach = -(Math.log(((getLevel() +1) - maxLevel) / -(maxLevel - minLevel)) / xpToLevelTwo);

    }

    /**
     *
     * Creates a pet
     *
     * @param owner - the owner
     * @param useProtocolLib - boolean if protocolLib is being used
     */

    public Pet(Player owner, Boolean useProtocolLib) {

        this(owner, 0L, useProtocolLib);

    }

    /**
     *
     * Creates a pet
     *
     * @param owner - the owner
     *
     */

    public Pet(Player owner) {

        this(owner, 0L, false);

    }

    /**
     *
     * Spawns an armorstand
     *
     * @param players - the players to which it will be sent
     */
    protected abstract void spawnArmorstandWithPackets(List<Player> players);

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
     * Returns the ability of the pet
     *
     * @return the ability
     */

    public abstract String getAbility();

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

    public String getCustomeName(){

        if(owner.getName().endsWith("s")){

            return  "§8[" + getLevelColor() + getLevel() + "§8] §7" + owner.getName() + "' " + getName();

        }else {

            return "§8[" + getLevelColor() + getLevel() + "§8] §7" + owner.getName() + "'s " + getName();

        }

    }

    public abstract void teleport(Location loc);

    public abstract void spawnToPlayer(Player p, JavaPlugin plugin);

    public abstract void despawnFromPlayer(Player p, JavaPlugin plugin);

    public abstract void setCustomName(String name);

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isPauseLogic() {
        return pauseLogic;
    }

    public void setPauseLogic(boolean pauseLogic) {
        this.pauseLogic = pauseLogic;
    }
}
