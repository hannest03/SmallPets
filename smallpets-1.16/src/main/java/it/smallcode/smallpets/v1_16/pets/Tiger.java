package it.smallcode.smallpets.v1_16.pets;
/*

Class created by SmallCode
05.07.2020 20:35

*/

import it.smallcode.smallpets.v1_16.SkullCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Tiger extends it.smallcode.smallpets.v1_15.pets.Tiger {


    public Tiger(Player owner, Long xp, Boolean useProtocolLib) {
        super(owner, xp, useProtocolLib);
    }

    @Override
    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("[I;-1896433491,-1924775600,-1838403799,907924510]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM0MjYzODc0NDkyMmI1ZmNmNjJjZDliZjI3ZWVhYjkxYjJlNzJkNmM3MGU4NmNjNWFhMzg4Mzk5M2U5ZDg0In19fQ==");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

    }
}
