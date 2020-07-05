package it.smallcode.smallpets.pets.v1_15.pets;
/*

Class created by SmallCode
04.07.2020 12:58

*/

import it.smallcode.smallpets.pets.v1_15.SamplePet;
import it.smallcode.smallpets.pets.v1_15.SkullCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Monkey extends SamplePet {

    public Monkey(Player owner, Long xp) {
        super(owner, xp);
    }

    public Monkey(Player owner) {
        this(owner, 0L);
    }

    public ItemStack getItem() {

        ItemStack skull = SkullCreator.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNkNGExNTYwM2Y5NTFkZTJlMmFiODBlZWQxNmJiYjVhNTgyM2JmNGFjYjhjNDYzMzQyNWQ1NDIxMGNmMGFkNSJ9fX0=");

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getName());

        skull.setItemMeta(skullMeta);

        return skull;

    }

    @Override
    public String getName() {

        return "monkey";

    }

}
