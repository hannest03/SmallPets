package it.smallcode.smallpets.pets.v1_15.pets;
/*

Class created by SmallCode
03.07.2020 17:51

*/

import it.smallcode.smallpets.pets.v1_15.SamplePet;
import it.smallcode.smallpets.pets.v1_15.SkullCreator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Penguin extends SamplePet {

    public Penguin(Player owner, Integer xp) {
        super(owner, xp);
    }

    public Penguin(Player owner) {
        this(owner, 0);
    }

    public void spawn(JavaPlugin plugin) {

        Location loc = owner.getLocation().clone();

        loc.setX(loc.getX() - 1);
        loc.setY(loc.getY() + 0.75);

        armorStand = createArmorStand(loc);

        armorStand.setHelmet(getItem());

        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("§e" + owner.getName() + "s penguin");

        armorStand.setAI(false);

        initAnimations(plugin);

    }

    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MwZDE2MTA3OTU2ZDc4NTNhMWJlMzE1NDljNDZhMmZmMjBiNDUxZDYzNjA3NTI4ZDVlMTk1YzQ0NTllMWZhMSJ9fX0=");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName("penguin");

        skull.setItemMeta(skullMeta);

        return skull;

    }

    @Override
    public String getName() {

        return "penguin";

    }

}
