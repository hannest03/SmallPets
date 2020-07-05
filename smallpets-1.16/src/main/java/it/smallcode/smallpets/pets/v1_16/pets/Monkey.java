package it.smallcode.smallpets.pets.v1_16.pets;
/*

Class created by SmallCode
05.07.2020 20:37

*/

import it.smallcode.smallpets.pets.v1_16.SkullCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Monkey extends it.smallcode.smallpets.pets.v1_15.pets.Monkey {


    public Monkey(Player owner, Long xp) {
        super(owner, xp);
    }

    @Override
    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("[I;-1905115355,-354136406,-1215851189,-1305538025]", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNkNGExNTYwM2Y5NTFkZTJlMmFiODBlZWQxNmJiYjVhNTgyM2JmNGFjYjhjNDYzMzQyNWQ1NDIxMGNmMGFkNSJ9fX0=");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

    }
}
