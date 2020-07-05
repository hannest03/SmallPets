package it.smallcode.smallpets.pets.v1_15;
/*

Class created by SmallCode
03.07.2020 16:21

*/

import it.smallcode.smallpets.events.PetLevelUpEvent;
import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.pets.v1_15.animation.FollowPlayerArmorStand;
import it.smallcode.smallpets.pets.v1_15.animation.HoverArmorStand;
import it.smallcode.smallpets.pets.v1_15.animation.LevelOnehundretAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SamplePet extends Pet {


    public SamplePet(Player owner, Long xp) {
        super(owner, xp);
    }

    private FollowPlayerArmorStand followPlayerArmorStand;
    private HoverArmorStand hoverArmorStand;
    private LevelOnehundretAnimation levelOnehundretAnimation;

    private int rotateID;

    public void spawn(JavaPlugin plugin) {

        Location loc = owner.getLocation().clone();

        loc.setX(loc.getX() - 1);
        loc.setY(loc.getY() + 0.75);

        armorStand = createArmorStand(loc);

        //Please don't ask why (╯°□°)╯︵ ┻━┻

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                armorStand.setCustomNameVisible(true);
            }
        }, 1);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                armorStand.setCustomName("§8[" + getLevelColor() + getLevel() + "§8] §7" + owner.getName() + "s " + getName());
            }
        }, 2);

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

    protected void initAnimations(JavaPlugin plugin){

        followPlayerArmorStand = new FollowPlayerArmorStand(armorStand, 0.5, owner, plugin);
        followPlayerArmorStand.setActive(false);

        hoverArmorStand = new HoverArmorStand(armorStand, 0.025, 0.2, -0.5, plugin);
        hoverArmorStand.setActive(false);

        if(getLevel() == 100)
            levelOnehundretAnimation = new LevelOnehundretAnimation(this, armorStand, plugin);

        rotateID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
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

        followPlayerArmorStand.setActive(true);

        hoverArmorStand.setActive(false);

    }

    public void idle() {

        followPlayerArmorStand.setActive(false);

        hoverArmorStand.setActive(true);

    }

    public ItemStack getItem() {

        return null;

    }

    public void giveExp(int exp, JavaPlugin plugin){

        int level = getLevel();

        if(level != 100)
            this.xp += exp;

        if(level < getLevel()){

            //LEVEL UP

            Bukkit.getPluginManager().callEvent(new PetLevelUpEvent(this));

            armorStand.setCustomName("§8[" + getLevelColor() + getLevel() + "§8] §7" + owner.getName() + "s " + getName());

            if(getLevel() == 100)
                levelOnehundretAnimation = new LevelOnehundretAnimation(this, armorStand, plugin);

        }

    }

    public void destroy() {

        Bukkit.getScheduler().cancelTask(rotateID);

        followPlayerArmorStand.cancel();
        hoverArmorStand.cancel();

        if(levelOnehundretAnimation != null)
            levelOnehundretAnimation.cancel();

        if(armorStand != null)
            armorStand.remove();

    }

    protected ArmorStand createArmorStand(Location loc){

        ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        return armorStand;

    }

    @Override
    public String getName() {
        return null;
    }

}
