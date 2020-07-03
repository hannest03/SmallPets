package it.smallcode.smallpets.pets.v1_15;
/*

Class created by SmallCode
02.07.2020 14:57

*/

import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.pets.v1_15.animation.FollowPlayerArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Tiger extends Pet {

    public Tiger(Player owner, Integer xp) {
        super(owner, xp);
    }

    public Tiger(Player owner) {
        super(owner);
    }

    private FollowPlayerArmorStand moveArmorStand;

    private int rotateID;

    public void spawn(JavaPlugin plugin) {

        Location loc = owner.getLocation().clone();

        loc.setX(loc.getX() -1);

        armorStand = createArmorStand(loc);

        armorStand.setHelmet(getItem());

        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("§e" + owner.getName() + "s tiger");

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

                armorStand.teleport(loc);

            }
        }, 0, 0);

        //moveArmorStand = new FollowPlayerArmorStand(armorStand, 1, owner, plugin);

    }

    public void move() {

    }

    public void idle() {

        System.out.println("Idle!");

    }

    public ItemStack getItem() {

        return SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM0MjYzODc0NDkyMmI1ZmNmNjJjZDliZjI3ZWVhYjkxYjJlNzJkNmM3MGU4NmNjNWFhMzg4Mzk5M2U5ZDg0In19fQ==");

    }

    public void destroy() {

        Bukkit.getScheduler().cancelTask(rotateID);

        if(armorStand != null)
            armorStand.remove();

    }

    private ArmorStand createArmorStand(Location loc){

        ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        armorStand.setVisible(false);
        armorStand.setGravity(false);

        armorStand.setSmall(true);

        return armorStand;

    }

}
