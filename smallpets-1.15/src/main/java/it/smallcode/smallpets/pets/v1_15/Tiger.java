package it.smallcode.smallpets.pets.v1_15;
/*

Class created by SmallCode
02.07.2020 14:57

*/

import it.smallcode.smallpets.pets.Pet;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Tiger extends Pet {

    public Tiger(Player owner, Integer xp) {
        super(owner, xp);
    }

    public Tiger(Player owner) {
        super(owner);
    }

    public void spawn() {

        Location loc = owner.getLocation().clone();

        loc.setX(loc.getX() -1);

        armorStand = createArmorStand(loc);

        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("§e" + owner.getName() + "s tiger");

    }

    public void move() {

        System.out.println("Move!");

    }

    public void idle() {

        System.out.println("Idle!");

    }

    public ItemStack getItem() {



        return null;

    }

    public void destroy() {

        if(armorStand != null)
            armorStand.remove();

    }

    private ArmorStand createArmorStand(Location loc){

        ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        armorStand.setVisible(true);
        armorStand.setGravity(false);

        return armorStand;

    }

}
