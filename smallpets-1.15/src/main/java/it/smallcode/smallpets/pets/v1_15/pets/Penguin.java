package it.smallcode.smallpets.pets.v1_15.pets;
/*

Class created by SmallCode
03.07.2020 17:51

*/

import it.smallcode.smallpets.pets.v1_15.SamplePet;
import it.smallcode.smallpets.pets.v1_15.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Penguin extends SamplePet {

    public Penguin(Player owner, Long xp) {
        super(owner, xp);
    }

    public Penguin(Player owner) {
        this(owner, 0L);
    }

    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MwZDE2MTA3OTU2ZDc4NTNhMWJlMzE1NDljNDZhMmZmMjBiNDUxZDYzNjA3NTI4ZDVlMTk1YzQ0NTllMWZhMSJ9fX0=");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

    }

    @Override
    public String getName() {

        return "penguin";

    }

}
