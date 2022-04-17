package it.smallcode.smallpets.core.pets;
/*

Class created by SmallCode
02.07.2020 14:43

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.animations.FollowPlayerAnimation;
import it.smallcode.smallpets.core.animations.HoverAnimation;
import it.smallcode.smallpets.core.animations.LevelOnehundretAnimation;
import it.smallcode.smallpets.core.animations.WalkAwayFromPlayerAnimation;
import it.smallcode.smallpets.core.events.PetLevelUpEvent;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.text.CenteredText;
import net.minecraft.server.v1_15_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * The basic pet class
 *
 */

public class Pet {

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

    private static final int MINLEVEL = 1;
    public static final int MAXLEVEL = 100;
    private static long xpToLevelTwo = 500;

    private static double tach = 0;

    private final String id;
    protected UUID uuid;

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

    protected String textureValue;

    protected FollowPlayerAnimation followPlayerArmorStand;
    protected HoverAnimation hoverAnimation;
    protected LevelOnehundretAnimation levelOnehundretAnimation;
    protected WalkAwayFromPlayerAnimation walkAwayFromPlayerAnimation;

    protected int logicID;

    /**
     *
     * Creates a pet
     *
     * @param owner - the pet owner
     * @param xp - the xp
     * @param useProtocolLib - boolean if protocolLib is being used
     */

    public Pet(String id, Player owner, Long xp, Boolean useProtocolLib) {

        abilities = new LinkedList<>();

        this.owner = owner;
        this.xp = xp;

        this.useProtocolLib = useProtocolLib;

        this.id = id;

        updateTexture();

    }

    protected void updateTexture(){

        textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI3ZjY2M2Q2NWNkZWQ3YmQzNjUxYmRkZDZkYjU0NjM2MGRkNzczYWJiZGFmNDhiODNhZWUwOGUxY2JlMTQifX19";

    }

    /**
     *
     * Returns the id of the pet
     *
     * @return the id
     */

    public String getID(){

        return id;

    }

    public UUID getUuid(){
        return uuid;
    }

    public void setUuid(UUID uuid){
        this.uuid = uuid;
    }

    /**
     *
     * Spawns the pet
     *
     * @param plugin - the plugin
     */

    public void spawn(JavaPlugin plugin) {

        setActivated(true);

        Location loc = owner.getLocation().clone();

        loc.setX(loc.getX() - 1);
        loc.setY(loc.getY() + 0.75);

        setLocation(loc);

        if(useProtocolLib) {

            spawnPackets(plugin, loc);

        }else{

            spawnArmorStand(plugin, loc);

        }

    }

    public void spawnToPlayer(Player p, JavaPlugin plugin){

        if(useProtocolLib){

            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

            if(user != null && user.getSettings().isShowPets()) {

                Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {

                        List<Player> players = new LinkedList<>();

                        players.add(p);

                        spawnArmorstandWithPackets(players);

                        setCustomName(getCustomeName());

                    }
                });

            }

        }

    }

    public void despawnFromPlayer(Player p, JavaPlugin plugin){

        if(useProtocolLib){

            Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {

                    PacketContainer entityDestroy = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().destroyEntity(entityID);

                    if(p != null) {

                        sendPacket(entityDestroy, p);

                    }

                }
            });

        }

    }


    protected void spawnPackets(JavaPlugin plugin, Location loc){

        do{

            entityID = (int) (Math.random() * 10000);

        }while(entityIDs.contains(entityID) && entityID >= 0);

        final Pet pet = this;

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {

                spawnArmorstandWithPackets(sendPacketToPlayers(owner));

                setCustomName(getCustomeName());

                followPlayerArmorStand = new FollowPlayerAnimation(pet, 0.5D);
                walkAwayFromPlayerAnimation = new WalkAwayFromPlayerAnimation(pet, 0.55D);

                hoverAnimation = new HoverAnimation(pet, 0.025, 0.2, -0.5);

                if(getLevel() == 100)
                    levelOnehundretAnimation = new LevelOnehundretAnimation(pet, getLanguageManager(),  plugin);

                initLogic(plugin);

            }
        });

    }

    protected void spawnArmorstandWithPackets(List<Player> players) {

        //SPAWN ARMORSTAND

        PacketContainer spawnPacket = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().spawnArmorstand(entityID, location);

        //EQUIPMENT

        PacketContainer entityEquipment = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().equipItem(entityID, EnumWrappers.ItemSlot.HEAD, getItem());

        //METADATA

        PacketContainer entityMetadata = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().standardMetaData(entityID);

        sendPacket(sendPacketToPlayers(owner), spawnPacket);
        sendPacket(sendPacketToPlayers(owner), entityEquipment);
        sendPacket(sendPacketToPlayers(owner), entityMetadata);

    }

    protected List<Player> sendPacketToPlayers(Player player){

        List<Player> players = new LinkedList<>();

        for(Player all : Bukkit.getOnlinePlayers())
            if(all.getWorld().getName().equals(player.getWorld().getName())){

                User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(all.getUniqueId().toString());

                if(user != null && user.getSettings().isShowPets()) {

                    players.add(all);

                }

            }

        return players;

    }

    protected void sendPacket(List<Player> players, PacketContainer packetContainer){

        for(Player player : players){

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

    }

    protected void sendPacket(PacketContainer packetContainer, Player player){

        List<Player> players = new LinkedList<>();

        players.add(player);

        sendPacket(players, packetContainer);

    }

    private void spawnArmorStand(JavaPlugin plugin, Location loc){

        armorStand = createArmorStand(loc);

        setCustomName(getCustomeName());

        //Please don't ask why

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                armorStand.setCustomNameVisible(true);
            }
        }, 1);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                armorStand.setGravity(false);
            }
        }, 3);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                armorStand.setSmall(true);
            }
        }, 4);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                armorStand.setInvulnerable(true);
            }
        }, 5);

        armorStand.getEquipment().setHelmet(getItem());

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {

                armorStand.setVisible(false);

            }
        }, 6);

        initAnimations(plugin);

    }

    public void setCustomName(String name){

        if(useProtocolLib){

            PacketContainer entityMetadata = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().setCustomName(entityID, name);

            sendPacket(sendPacketToPlayers(owner), entityMetadata);

        }else{

            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(name);

        }

    }

    protected void initAnimations(JavaPlugin plugin){

        followPlayerArmorStand = new FollowPlayerAnimation(this, 0.5);
        walkAwayFromPlayerAnimation = new WalkAwayFromPlayerAnimation(this, 0.55);

        hoverAnimation = new HoverAnimation(this, 0.025, 0.2, -0.5);

        if(getLevel() == 100)
            levelOnehundretAnimation = new LevelOnehundretAnimation(this, getLanguageManager(), plugin);

        initLogic(plugin);

    }

    private void initLogic(JavaPlugin plugin){

        logicID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {

                spawnParticles();

                if(!isPauseLogic()) {

                    double distance = Math.sqrt(Math.pow(getLocation().getX() - owner.getLocation().getX(), 2) + Math.pow(getLocation().getZ() - owner.getLocation().getZ(), 2));

                    if (distance >= 2.5D || Math.abs(owner.getLocation().getY() - getLocation().getY()) > 1D)

                        move();

                    else
                    if(distance <= 1.0D){

                        moveAway();

                    }else {

                        idle();

                    }

                }

            }
        }, 0, 0);

    }

    public void moveAway(){

        this.location = walkAwayFromPlayerAnimation.move(owner, location);

    }

    public void move() {

        this.location = followPlayerArmorStand.move(owner, location);

    }

    public void idle() {

        this.location = hoverAnimation.hover(owner, location);

    }

    public void giveExp(int exp, JavaPlugin plugin){

        int level = getLevel();

        if(exp >= 0) {
            if (level != 100)
                this.xp += exp;

        }else{

            this.xp += exp;

            if(this.xp < 0)
                this.xp = 0;

        }

        SmallPetsCommons.getSmallPetsCommons().getUserManager().updatePet(this);

        if(level != getLevel()){

            //LEVEL UP

            AbilityEventBus.post(new it.smallcode.smallpets.core.abilities.eventsystem.events.PetLevelUpEvent(SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(getOwner().getUniqueId().toString()), this, level));

            Bukkit.getPluginManager().callEvent(new PetLevelUpEvent(this));

            if(isActivated()) {

                setCustomName(getCustomeName());

                if (getLevel() == 100) {

                    levelOnehundretAnimation = new LevelOnehundretAnimation(this, getLanguageManager(), plugin);

                }else {

                    if (levelOnehundretAnimation != null) {

                        levelOnehundretAnimation.cancel();
                        levelOnehundretAnimation = null;

                    }
                }

            }

        }

    }

    public void destroy() {

        setActivated(false);

        Bukkit.getScheduler().cancelTask(logicID);

        if (levelOnehundretAnimation != null)
            levelOnehundretAnimation.cancel();

        if (armorStand != null) {
            armorStand.remove();
            armorStand = null;
        }

        if(useProtocolLib){

            PacketContainer entityDestroy = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().destroyEntity(entityID);

            if(owner != null) {

                sendPacket(sendPacketToPlayers(owner), entityDestroy);

            }

        }

    }

    /**
     *
     * Returns the pet head
     *
     * @return the head
     */

    public ItemStack getItem() {

        updateTexture();

        ItemStack skull = SmallPetsCommons.getSmallPetsCommons().getSkullCreator().createHeadItem(textureValue);

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getID());

        skull.setItemMeta(skullMeta);

        return skull;

    }

    public void registerRecipe(Plugin plugin){}

    /**
     *
     * Returns the level of the pet
     *
     * @return the level
     */

    public int getLevel(){

        return (int) ((MAXLEVEL) * (1 - Math.pow(Math.E, -tach * xp)) + MINLEVEL);

    }

    /**
     *
     * Returns the amount of exp needed for a level
     *
     * @param level - the level
     * @return the amount of exp needed
     */

    public static long getExpForLevel(int level){

        return (long) (Math.log(((level) - (MAXLEVEL +1D)) / -((MAXLEVEL +1D) - MINLEVEL)) / -tach);

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

    public String getName(){
        return SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("pet." + getID());
    }

    public String getCustomeName(){

        String name = "";

        if(owner.getName().endsWith("s")){
            name = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("petNameFormatTwoS");
        }else {
            name = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("petNameFormat");
        }

        name = name.replaceAll("%level%", getLevelColor() + getLevel());
        name = name.replaceAll("%player_name%", owner.getName());

        name += getName();

        return name;

    }

    public String getCustomeNameWithoutPlayername(){

        String name = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("petNameWithoutOwner");

        name = name.replaceAll("%level%", getLevelColor() + getLevel());

        String petName = getName();

        petName = petName.substring(0, 1).toUpperCase() + petName.substring(1);

        name += "§6" + petName;

        return name;

    }

    protected String generateFinishedProgressbar(){

        if(getLevel() == 100)
            return generateProgressBar();

        return getLevelColor() + getLevel() + " " + generateProgressBar() + " " + getLevelColor() + (getLevel() +1);

    }

    public String generateProgressBar(){

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

    public ItemStack getUnlockItem() {

        ItemStack item = getDisplayItem();

        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = itemMeta.getLore();

        lore.add("");
        lore.add(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("rightClickToUnlock"));

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        item = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(item, "petExp", String.valueOf(getXp()));

        return item;

    }

    protected void spawnParticles() {

        if(!SmallPetsCommons.getSmallPetsCommons().isActivateParticles())
            return;

        Location particleLoc = location.clone();

        particleLoc.setY(particleLoc.getY() + 0.7);

        for(Player p : sendPacketToPlayers(owner)){

            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

            if(!useProtocolLib || (user != null && user.getSettings().isShowPets())) {

                p.spawnParticle(getParticle(), particleLoc, 0);

            }

        }

    }

    public ItemStack getDisplayItem() {

        ItemStack itemStack = getItem();

        if(itemStack != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(getCustomeNameWithoutPlayername());

            String loreString = SmallPetsCommons.getSmallPetsCommons().getPetLore().stream().collect(Collectors.joining("\n"));

            if(getPetType() != null) {

                String petType = getPetType().getName(getLanguageManager());
                loreString = loreString.replaceAll("%pet_type%", petType);

            }

            String abilityStatLore = "";
            String abilityLore = "";
            for(int i = 0; i < abilities.size(); i++) {
                Ability ability = abilities.get(i);
                if (ability.getAbilityType() == AbilityType.STAT) {
                    abilityStatLore += ability.getAbilityTooltip(this).stream().collect(Collectors.joining("\n"));
                    if(i != abilities.size() -1)
                        abilityStatLore += "\n";
                }
                if (ability.getAbilityType() == AbilityType.ABILITY) {
                    if(i != 0)
                        abilityLore += "\n";
                    abilityLore += ability.getAbilityTooltip(this).stream().collect(Collectors.joining("\n"));
                    if(i != abilities.size() -1)
                        abilityStatLore += "\n";
                }
            }
            loreString = loreString.replaceAll("%abilities%", abilityStatLore + abilityLore);

            int maxLength = 0;

            for(String s : loreString.split("\n")){

                if(ChatColor.stripColor(s).length() > maxLength){

                    maxLength = ChatColor.stripColor(s).length();

                }

            }

            List<String> progressBarList = new LinkedList<>();

            String progressBar = CenteredText.sendCenteredMessage(generateFinishedProgressbar(), maxLength);

            if(getLevel() != 100) {

                progressBarList.add("  " + CenteredText.sendCenteredMessage(getLevelColor() + getLevel(), ChatColor.stripColor(progressBar).length()));
                progressBarList.add(progressBar);

                String expB = getLevelColor() + (getXp() - getExpForLevel(getLevel())) + "§8/" + getLevelColor() + (getExpForNextLevel() - getExpForLevel(getLevel()));

                progressBarList.add("  " + CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

            }else{

                progressBarList.add("§8" + getLanguageManager().getLanguage().getStringFormatted("maxLevel"));

            }

            String progressbarFinished = progressBarList.stream().collect(Collectors.joining("\n"));
            loreString = loreString.replaceAll("%progress_bar%", progressbarFinished);
            loreString = ChatColor.translateAlternateColorCodes('&', loreString);

            List<String> lore = new LinkedList<>(Arrays.asList(loreString.split("\n")));
            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);

            itemStack = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(itemStack, "pet", getID());

            if(getUuid() != null)
                itemStack = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(itemStack, "pet.uuid", getUuid().toString());

        }

        return itemStack;

    }

    public void teleport(Location loc) {
        if(useProtocolLib){
            PacketContainer teleportPacket = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().teleportEntity(entityID, loc);
            sendPacket(sendPacketToPlayers(owner), teleportPacket);
        }else{
            if(armorStand != null) {
                loadChunks(location);
                loadChunks(loc);
                armorStand.teleport(loc);
            }
        }
        setLocation(loc);
    }

    private void loadChunks(Location loc){

        if(!loc.getChunk().isLoaded())
            loc.getChunk().load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX()+1, loc.getChunk().getZ()).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX()+1, loc.getChunk().getZ()).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX()-1, loc.getChunk().getZ()).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX()-1, loc.getChunk().getZ()).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ()+1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ()+1).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ()-1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ()-1).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX()+1, loc.getChunk().getZ()+1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX()+1, loc.getChunk().getZ()+1).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX()-1, loc.getChunk().getZ()-1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX()-1, loc.getChunk().getZ()-1).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX() -1, loc.getChunk().getZ() +1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX() -1, loc.getChunk().getZ() +1).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX() +1, loc.getChunk().getZ() -1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX() +1, loc.getChunk().getZ() -1).load();

    }

    protected ArmorStand createArmorStand(Location loc){

        ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        return armorStand;

    }

    public boolean hasAbility(String abilityID){

        return abilities.stream().filter(ability -> ability.getID().equals(abilityID)).findFirst().isPresent();

    }

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
        return SmallPetsCommons.getSmallPetsCommons().getLanguageManager();
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

    public List<Ability> getAbilities() {
        return abilities;
    }

    public static long getXpToLevelTwo() {
        return xpToLevelTwo;
    }

    public static void setXpToLevelTwo(long xpToLevelTwo) {

        if(xpToLevelTwo > 0) {

            Pet.xpToLevelTwo = xpToLevelTwo;
            tach = -(Math.log(((MINLEVEL+1D) - (MAXLEVEL +1D) ) / -((MAXLEVEL +1D) - MINLEVEL)) / (double) xpToLevelTwo);

        }

    }
}
