package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
03.07.2020 16:21

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.smallcode.smallpets.animations.FollowPlayerAnimation;
import it.smallcode.smallpets.animations.HoverAnimation;
import it.smallcode.smallpets.events.PetLevelUpEvent;
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.manager.types.User;
import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.animations.LevelOnehundretAnimation;
import it.smallcode.smallpets.text.CenteredText;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 *
 * The foundation of all pets in the 1.15 version
 *
 */
public class SamplePet extends Pet {

    private static final ArrayList<String> colors = new ArrayList<>();

    static {

        colors.add("§4");
        colors.add("§c");
        colors.add("§6");
        colors.add("§e");
        colors.add("§2");
        colors.add("§a");
        colors.add("§b");
        colors.add("§3");
        colors.add("§1");
        colors.add("§9");
        colors.add("§d");
        colors.add("§5");

    }

    /**
     *
     * Creates a pet
     *
     * @param owner - the pet owner
     * @param xp - the xp
     */
    public SamplePet(Player owner, Long xp, Boolean useProtocolLib, LanguageManager languageManager) {
        super(owner, xp, useProtocolLib, languageManager);
    }

    protected FollowPlayerAnimation followPlayerArmorStand;
    protected HoverAnimation hoverAnimation;
    protected LevelOnehundretAnimation levelOnehundretAnimation;

    protected int logicID;

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

    public void despawnFromPlayer(Player p, JavaPlugin plugin){

        if(useProtocolLib){

            Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {

                    PacketContainer entityDestroy = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);

                    int[] entityIDs = new int[1];

                    entityIDs[0] = entityID;

                    entityDestroy.getIntegerArrays().write(0, entityIDs);

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

                hoverAnimation = new HoverAnimation(pet, 0.025, 0.2, -0.5);

                if(getLevel() == 100)
                    levelOnehundretAnimation = new LevelOnehundretAnimation(pet, getLanguageManager(),  plugin);

                logicID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
                    @Override
                    public void run() {

                        spawnParticles();

                        if(!isPauseLogic()) {

                            if (Math.abs(location.distance(owner.getLocation())) >= 2.5D)
                                move();
                            else
                                idle();

                        }

                    }
                }, 0, 0);

            }
        });

    }

    @Override
    protected void spawnArmorstandWithPackets(List<Player> players) {

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        //SPAWN ARMORSTAND

        PacketContainer spawnPacket = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        spawnPacket.getIntegers().write(0, entityID);
        spawnPacket.getIntegers().write(6, 0);

        spawnPacket.getEntityTypeModifier().write(0, EntityType.ARMOR_STAND);

        spawnPacket.getDoubles().write(0, location.getX());
        spawnPacket.getDoubles().write(1, location.getY());
        spawnPacket.getDoubles().write(2, location.getZ());

        spawnPacket.getIntegers().write(4, 0);
        spawnPacket.getIntegers().write(5, (int) (location.getYaw() * 256.0F / 360.0F));

        spawnPacket.getUUIDs().write(0, UUID.randomUUID());

        //EQUIPMENT

        PacketContainer entityEquipment = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

        entityEquipment.getIntegers().write(0, entityID);
        entityEquipment.getItemSlots().write(0, EnumWrappers.ItemSlot.HEAD);
        entityEquipment.getItemModifier().write(0, getItem());

        //METADATA

        PacketContainer entityMetadata = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

        entityMetadata.getIntegers().write(0, entityID);

        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getWatchableCollectionModifier().read(0));

        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20);
        dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(14, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x01);

        entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        sendPacket(sendPacketToPlayers(owner), spawnPacket);
        sendPacket(sendPacketToPlayers(owner), entityEquipment);
        sendPacket(sendPacketToPlayers(owner), entityMetadata);

    }

    protected List<Player> sendPacketToPlayers(Player player){

        List<Player> players = new LinkedList<>();

        for(Player all : Bukkit.getOnlinePlayers())
            if(all.getWorld().getName().equals(player.getWorld().getName()))
                players.add(all);

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

            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

            PacketContainer entityMetadata = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

            entityMetadata.getIntegers().write(0, entityID);

            WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getWatchableCollectionModifier().read(0));

            dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), Optional
                    .of(WrappedChatComponent
                            .fromChatMessage(name)[0].getHandle()));

            dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

            entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

            sendPacket(sendPacketToPlayers(owner), entityMetadata);

        }else{

            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(name);

        }

    }

    protected void initAnimations(JavaPlugin plugin){

        followPlayerArmorStand = new FollowPlayerAnimation(this, 0.5);

        hoverAnimation = new HoverAnimation(this, 0.025, 0.2, -0.5);

        if(getLevel() == 100)
            levelOnehundretAnimation = new LevelOnehundretAnimation(this, getLanguageManager(), plugin);

        logicID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {

                spawnParticles();

                if(!isPauseLogic()) {

                    if (armorStand.getLocation().distance(owner.getLocation()) >= 2.5D)
                        move();
                    else
                        idle();

                }

            }
        }, 0, 0);

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

        if(level != getLevel()){

            //LEVEL UP

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

        if (armorStand != null)
            armorStand.remove();

        if(useProtocolLib){

            PacketContainer entityDestroy = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);

            int[] entityIDs = new int[1];

            entityIDs[0] = entityID;

            entityDestroy.getIntegerArrays().write(0, entityIDs);

            if(owner != null) {

                sendPacket(sendPacketToPlayers(owner), entityDestroy);

            }

        }

    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public void registerRecipe(Plugin plugin) {

    }

    @Override
    public ItemStack getUnlockItem(Plugin plugin) {

        ItemStack item = getDisplayItem();

        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = itemMeta.getLore();

        lore.add("");
        lore.add("§6RIGHT CLICK TO UNLOCK");

        itemMeta.setLore(lore);

        itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "pet"), PersistentDataType.STRING, getName());
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "petExp"), PersistentDataType.LONG, getXp());

        item.setItemMeta(itemMeta);

        return item;

    }

    @Override
    public String getAbility() {
        return "";
    }

    @Override
    protected void spawnParticles() {

        Location particleLoc = location.clone();

        particleLoc.setY(particleLoc.getY() + 0.7);

        for(Player p : sendPacketToPlayers(owner)){

            p.spawnParticle(getParticle(), particleLoc, 1);

        }

    }

    @Override
    public ItemStack getDisplayItem() {

        ItemStack itemStack = getItem();

        if(itemStack != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(getCustomeNameWithoutPlayername());

            ArrayList<String> lore = new ArrayList();

            if(getPetType() != null) {

                lore.add("§8" + getPetType().getName(getLanguageManager()));

            }

            lore.add("");

            lore.add(getAbility());

            lore.add("");

            String progressBar = CenteredText.sendCenteredMessage(generateFinishedProgressbar(), ChatColor.stripColor(getAbility()).length());

            if(getLevel() != 100) {

                lore.add("  " + CenteredText.sendCenteredMessage(getLevelColor() + getLevel(), ChatColor.stripColor(progressBar).length()));
                lore.add(progressBar);

                String expB = getLevelColor() + (getXp() - getExpForLevel(getLevel())) + "§8/" + getLevelColor() + (getExpForNextLevel() - getExpForLevel(getLevel()));

                lore.add("  " + CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

            }else{

                lore.add("§8" + getLanguageManager().getLanguage().getStringFormatted("maxLevel"));

            }

            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);

        }

        return itemStack;

    }

    private String generateFinishedProgressbar(){

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

            int color = (int) (Math.random() * colors.size()-1);

            for(int i = 0; i < bars; i++) {

                bar += colors.get(color) + "|";

                color++;

                if(color == colors.size())
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

    @Override
    public void teleport(Location loc) {

        if(useProtocolLib){

            PacketContainer teleportPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.REL_ENTITY_MOVE_LOOK);

            teleportPacket.getIntegers().write(0, entityID);

            teleportPacket.getShorts().write(0, (short) ((loc.getX() - location.getX()) * 4096));
            teleportPacket.getShorts().write(1, (short) ((loc.getY() - location.getY()) * 4096));
            teleportPacket.getShorts().write(2, (short) ((loc.getZ() - location.getZ()) * 4096));

            teleportPacket.getBytes().write(0, (byte) (loc.getYaw() * 256.0F / 360.0F));
            teleportPacket.getBytes().write(1, (byte) (loc.getPitch() * 256.0F / 360.0F));

            sendPacket(sendPacketToPlayers(owner), teleportPacket);

        }else{

            loadChunks(location);
            loadChunks(loc);

            armorStand.teleport(loc);

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

    public String getName() {
        return null;
    }

}
