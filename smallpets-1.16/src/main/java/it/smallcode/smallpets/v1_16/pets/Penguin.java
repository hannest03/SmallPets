package it.smallcode.smallpets.v1_16.pets;
/*

Class created by SmallCode
05.07.2020 20:36

*/

import it.smallcode.smallpets.v1_16.SkullCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Penguin extends it.smallcode.smallpets.v1_15.pets.Penguin {


    public Penguin(Player owner, Long xp) {
        super(owner, xp);
    }

    @Override
    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("[I;1549146609,-1720693818,-2079463212,904090463]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MwZDE2MTA3OTU2ZDc4NTNhMWJlMzE1NDljNDZhMmZmMjBiNDUxZDYzNjA3NTI4ZDVlMTk1YzQ0NTllMWZhMSJ9fX0=");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

    }
}
