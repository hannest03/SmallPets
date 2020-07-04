package it.smallcode.smallpets.pets.v1_15.pets;
/*

Class created by SmallCode
02.07.2020 14:57

*/

import it.smallcode.smallpets.pets.v1_15.SamplePet;
import it.smallcode.smallpets.pets.v1_15.SkullCreator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Tiger extends SamplePet {

    public Tiger(Player owner, Integer xp) {
        super(owner, xp);
    }

    public Tiger(Player owner) {
        this(owner, 0);
    }

    public void spawn(JavaPlugin plugin) {

        Location loc = owner.getLocation().clone();

        loc.setX(loc.getX() - 1);
        loc.setY(loc.getY() + 0.75);

        armorStand = createArmorStand(loc);

        armorStand.setHelmet(getItem());

        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("§e" + owner.getName() + "s tiger");

        armorStand.setAI(false);

        initAnimations(plugin);

    }

    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM0MjYzODc0NDkyMmI1ZmNmNjJjZDliZjI3ZWVhYjkxYjJlNzJkNmM3MGU4NmNjNWFhMzg4Mzk5M2U5ZDg0In19fQ==");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName("tiger");

        skull.setItemMeta(skullMeta);

        return skull;

    }

    @Override
    public String getName() {

        return "tiger";

    }

}
