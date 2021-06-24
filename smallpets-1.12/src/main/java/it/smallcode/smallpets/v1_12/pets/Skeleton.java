package it.smallcode.smallpets.v1_12.pets;
/*

Class created by SmallCode
03.11.2020 18:20

*/

import it.smallcode.smallpets.v1_12.abilities.UnbreakableBowAbility;
import it.smallcode.smallpets.v1_15.abilities.standard.DontConsumeArrowAbility;

public class Skeleton extends it.smallcode.smallpets.v1_15.pets.Skeleton {

    public Skeleton() {
        super();

        super.getAbilities().clear();
        super.getAbilities().add(new DontConsumeArrowAbility(10, 1));
        super.getAbilities().add(new UnbreakableBowAbility());
    }

}
