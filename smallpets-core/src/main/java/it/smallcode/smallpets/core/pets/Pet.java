package it.smallcode.smallpets.core.pets;
/*

Class created by SmallCode
02.07.2020 14:43

*/

import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.languages.LanguageManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 *
 * The basic pet class
 *
 */

public abstract class Pet {

    private LanguageManager languageManager;

    private static final double MINLEVEL = 1;
    public static final double MAXLEVEL = 100;
    private static final double XPTOLEVELTWO = 500;

    private final double tach;

    private Particle particle = Particle.VILLAGER_HAPPY;

    protected List<Ability> abilities;

    protected long xp = 0;

    protected boolean useProtocolLib = false;

    protected static List<Integer> entityIDs = new LinkedList<>();

    protected ArmorStand armorStand;

    protected Location location;
    protected int entityID;

    protected Player owner;

    private boolean pauseLogic = false;

    private boolean activated;

    private PetType petType;

    protected static final ArrayList<String> levelColors = new ArrayList<>();

    static {

        levelColors.add("§7");
        levelColors.add("§2");
        levelColors.add("§a");
        levelColors.add("§e");
        levelColors.add("§6");
        levelColors.add("§c");
        levelColors.add("§4");
        levelColors.add("§d");
        levelColors.add("§b");
        levelColors.add("§f");

    }

    /**
     *
     * Creates a pet
     *
     * @param owner - the pet owner
     * @param xp - the xp
     * @param useProtocolLib - boolean if protocolLib is being used
     */

    public Pet(Player owner, Long xp, Boolean useProtocolLib, LanguageManager languageManager) {

        abilities = new LinkedList<>();

        this.owner = owner;
        this.xp = xp;

        this.useProtocolLib = useProtocolLib;

        this.languageManager = languageManager;

        tach = -(Math.log(((getLevel() +1) - (MAXLEVEL +1) ) / -((MAXLEVEL +1) - MINLEVEL)) / XPTOLEVELTWO);

    }

    /**
     *
     * Creates a pet
     *
     * @param owner - the owner
     * @param useProtocolLib - boolean if protocolLib is being used
     */

    public Pet(Player owner, Boolean useProtocolLib, LanguageManager languageManager) {

        this(owner, 0L, useProtocolLib, languageManager);

    }

    /**
     *
     * Creates a pet
     *
     * @param owner - the owner
     *
     */

    public Pet(Player owner, LanguageManager languageManager) {

        this(owner, 0L, false, languageManager);

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
     * Returns the level of the pet
     *
     * @return the level
     */

    public int getLevel(){

        return (int) ((MAXLEVEL +1) - ((MAXLEVEL +1) - MINLEVEL) * Math.pow(Math.E, -tach * xp));

    }

    /**
     *
     * Returns the amount of exp needed for a level
     *
     * @param level - the level
     * @return the amount of exp needed
     */

    public long getExpForLevel(int level){

        return (int) (Math.log(((level) - (MAXLEVEL +1)) / -((MAXLEVEL +1) - MINLEVEL)) / -tach);

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

        if(levelColors.size() <= index)
            return "";

        return levelColors.get(index);

    }

    protected abstract void spawnParticles();

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

    public abstract ItemStack getDisplayItem(JavaPlugin plugin);

    public String getCustomeName(){

        String name = "";

        if(owner.getName().endsWith("s")){
            name = languageManager.getLanguage().getStringFormatted("petNameFormatTwoS");
        }else {
            name = languageManager.getLanguage().getStringFormatted("petNameFormat");
        }

        name = name.replaceAll("%level%", getLevelColor() + getLevel());
        name = name.replaceAll("%player_name%", owner.getName());

        name += languageManager.getLanguage().getStringFormatted("pet." + getName());

        return name;

    }

    public String getCustomeNameWithoutPlayername(){

        String name = languageManager.getLanguage().getStringFormatted("petNameWithoutOwner");

        name = name.replaceAll("%level%", getLevelColor() + getLevel());

        String petName = languageManager.getLanguage().getStringFormatted("pet." + getName());

        petName = petName.substring(0, 1).toUpperCase() + petName.substring(1);

        name += "§6" + petName;

        return name;

    }

    protected String generateFinishedProgressbar(){

        if(getLevel() == 100)
            return generateProgressBar();

        return getLevelColor() + getLevel() + " " + generateProgressBar() + " " + getLevelColor() + (getLevel() +1);

    }

    private String generateProgressBar(){

        String bar = "";

        int bars = 35;

        long lastExp = getExpForLevel(getLevel());
        long nextExp = getExpForNextLevel();

        if(getLevel() == 100){

            int color = (int) (Math.random() * levelColors.size()-1);

            for(int i = 0; i < bars; i++) {

                bar += levelColors.get(color) + "|";

                color++;

                if(color == levelColors.size())
                    color = 0;

            }

            return bar;

        }

        long oneBar = (nextExp - lastExp) / bars;

        long nextBar = 0;

        while(nextBar <= (getXp() - lastExp) && bar.length() < (bars*3)){

            nextBar += oneBar;

            bar += getLevelColor() + "|";

        }

        while(bar.length() < (bars*3)){

            bar += "§8|";

        }

        return bar;

    }

    public boolean hasAbility(String abilityID){

        return abilities.stream().filter(ability -> ability.getID().equals(abilityID)).findFirst().isPresent();

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

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    public PetType getPetType() {
        return petType;
    }

    protected void setPetType(PetType petType){

        this.petType = petType;

    }

    public Ability getAbility(String id){

        Optional<Ability> optAbility = abilities.stream().filter(ability -> ability.getID().equals(id)).findFirst();

        if(optAbility.isPresent())
            return optAbility.get();

        return null;

    }

}
