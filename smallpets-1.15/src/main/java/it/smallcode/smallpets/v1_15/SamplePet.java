package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
03.07.2020 16:21

*/

import com.comphenix.packetwrapper.*;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.smallcode.smallpets.animations.FollowPlayerAnimation;
import it.smallcode.smallpets.animations.HoverAnimation;
import it.smallcode.smallpets.events.PetLevelUpEvent;
import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.v1_15.animation.armorstands.FollowPlayerArmorStand;
import it.smallcode.smallpets.v1_15.animation.armorstands.HoverArmorStand;
import it.smallcode.smallpets.v1_15.animation.armorstands.LevelOnehundretAnimation;
import it.smallcode.smallpets.v1_15.animation.packets.FollowPlayerPackets;
import it.smallcode.smallpets.v1_15.animation.packets.HoverPackets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 *
 * The foundation of all pets in the 1.15 version
 *
 */
public class SamplePet extends Pet {

    /**
     *
     * Creates a pet
     *
     * @param owner - the pet owner
     * @param xp - the xp
     */
    public SamplePet(Player owner, Long xp, boolean useProtocolLib) {
        super(owner, xp, useProtocolLib);
    }

    private FollowPlayerAnimation followPlayerArmorStand;
    private HoverAnimation hoverAnimation;
    private LevelOnehundretAnimation levelOnehundretAnimation;

    private int rotateID;

    public void spawn(JavaPlugin plugin) {

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

    private void spawnPackets(JavaPlugin plugin, Location loc){

        do{

            entityID = (int) (Math.random() * 10000);

        }while(entityIDs.contains(entityID) && entityID >= 0);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {

                WrapperPlayServerSpawnEntity spawnEntity = new WrapperPlayServerSpawnEntity();

                spawnEntity.setEntityID(entityID);

                spawnEntity.setType(EntityType.ARMOR_STAND);
                spawnEntity.setObjectData(78);

                spawnEntity.setX(loc.getX());
                spawnEntity.setY(loc.getY());
                spawnEntity.setZ(loc.getZ());

                spawnEntity.sendPacket(owner);

                WrapperPlayServerEntityEquipment entityEquipment = new WrapperPlayServerEntityEquipment();

                entityEquipment.setEntityID(entityID);
                entityEquipment.setSlot(EnumWrappers.ItemSlot.HEAD);
                entityEquipment.setItem(getItem());

                entityEquipment.sendPacket(owner);

                WrapperPlayServerEntityMetadata entityMetadata = new WrapperPlayServerEntityMetadata();

                entityMetadata.setEntityID(entityID);

                WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getMetadata());

                dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20);
                dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(14, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x01);

                entityMetadata.setMetadata(dataWatcher.getWatchableObjects());

                entityMetadata.sendPacket(owner);

                setCustomName(getCustomeName());

            }
        });

        rotateID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {

                if(Math.abs(location.distance(owner.getLocation())) >= 2.5D)
                    move();
                else
                    idle();


            }
        }, 0, 0);

        followPlayerArmorStand = new FollowPlayerPackets(entityID, 0.5D);

        hoverAnimation = new HoverPackets(entityID, 0.025, 0.2, -0.5);

        if(getLevel() == 100)
            levelOnehundretAnimation = new LevelOnehundretAnimation(this, plugin);

    }

    private void sendPacket(PacketContainer packet){

        for(Player all : Bukkit.getOnlinePlayers()){

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(all, packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

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

            WrapperPlayServerEntityMetadata entityMetadata = new WrapperPlayServerEntityMetadata();

            entityMetadata.setEntityID(entityID);

            WrappedDataWatcher dataWatcher = new WrappedDataWatcher(entityMetadata.getMetadata());

            dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), Optional
                    .of(WrappedChatComponent
                            .fromChatMessage(name)[0].getHandle()));

            dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

            entityMetadata.setMetadata(dataWatcher.getWatchableObjects());

            entityMetadata.sendPacket(owner);

        }else{

            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(name);

        }

    }

    protected void initAnimations(JavaPlugin plugin){

        followPlayerArmorStand = new FollowPlayerArmorStand(armorStand, 0.5);

        hoverAnimation = new HoverArmorStand(armorStand, 0.025, 0.2, -0.5);

        if(getLevel() == 100)
            levelOnehundretAnimation = new LevelOnehundretAnimation(this, plugin);

        rotateID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {

                Location loc = armorStand.getLocation().clone();

                double a = owner.getLocation().getX() - armorStand.getLocation().getX();
                double b = owner.getLocation().getZ() - armorStand.getLocation().getZ();

                double angle = Math.atan(b / a);

                angle = angle * (180 / Math.PI);

                if(owner.getLocation().getX() - armorStand.getLocation().getX() >= 0){

                    angle += 180;

                }

                angle += 90;

                loc.setYaw((float) angle);

                try {

                    if(!loc.getChunk().isLoaded())
                        loc.getChunk().load();

                    if(!armorStand.getLocation().getChunk().isLoaded())
                        armorStand.getLocation().getChunk().load();

                    armorStand.teleport(loc);

                }catch (Exception ex){ ex.printStackTrace(); }

                Location particleLoc = loc.clone();

                particleLoc.setY(particleLoc.getY() + 0.7);

                if(!particleLoc.getChunk().isLoaded())
                    particleLoc.getChunk().load();

                particleLoc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, particleLoc, 1);

                if(armorStand.getLocation().distance(owner.getLocation()) >= 2.5D)
                    move();
                else
                    idle();

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

        if(level != 100)
            this.xp += exp;

        if(level < getLevel()){

            //LEVEL UP

            Bukkit.getPluginManager().callEvent(new PetLevelUpEvent(this));

            setCustomName(getCustomeName());

            if(getLevel() == 100)
                levelOnehundretAnimation = new LevelOnehundretAnimation(this, plugin);

        }

    }

    public void destroy() {

        Bukkit.getScheduler().cancelTask(rotateID);

        if (levelOnehundretAnimation != null)
            levelOnehundretAnimation.cancel();

        if (armorStand != null)
            armorStand.remove();

        if(useProtocolLib){

            WrapperPlayServerEntityDestroy entityDestroy = new WrapperPlayServerEntityDestroy();

            entityDestroy.setEntityIds(new int[]{ entityID });

            if(owner != null) {

                entityDestroy.sendPacket(owner);

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
        return null;
    }

    @Override
    public String getAbility() {
        return "";
    }

    protected ArmorStand createArmorStand(Location loc){

        ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        return armorStand;

    }

    public String getName() {
        return null;
    }

}
