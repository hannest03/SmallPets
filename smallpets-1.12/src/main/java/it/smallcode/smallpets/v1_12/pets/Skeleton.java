package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
03.11.2020 18:20

*/

import it.smallcode.smallpets.v1_12.abilities.UnbreakableBowAbility;
import it.smallcode.smallpets.v1_15.abilities.standard.DontConsumeArrowAbility;
import org.bukkit.entity.Player;

public class Skeleton extends it.smallcode.smallpets.v1_15.pets.Skeleton {

    public Skeleton(String type, Player owner, Long xp, Boolean useProtocolLib) {
        super(type, owner, xp, useProtocolLib);

        super.abilities.clear();
        super.abilities.add(new DontConsumeArrowAbility(10, 1));
        super.abilities.add(new UnbreakableBowAbility());
    }

}
