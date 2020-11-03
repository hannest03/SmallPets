package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
03.11.2020 18:20

*/

import it.smallcode.smallpets.v1_12.abilities.UnbreakableBowAbility;
import it.smallcode.smallpets.v1_15.abilities.DontConsumeArrowAbility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Skeleton extends it.smallcode.smallpets.v1_15.pets.Skeleton {

    public Skeleton(String id, Player owner, Long xp, Boolean useProtocolLib) {

        super(id, owner, xp, useProtocolLib);

        super.abilities.clear();

        super.abilities.add(new DontConsumeArrowAbility(10, 1));
        super.abilities.add(new UnbreakableBowAbility());

    }

}
