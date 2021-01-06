package it.smallcode.smallpets.v1_13;
/*

Class created by SmallCode
10.07.2020 15:05

*/

import it.smallcode.smallpets.v1_12.PetMapManager1_12;
import it.smallcode.smallpets.v1_13.pets.Monkey;
import it.smallcode.smallpets.v1_13.pets.Panda;
import it.smallcode.smallpets.v1_15.pets.*;

public class PetMapManager1_13 extends PetMapManager1_12 {

    @Override
    public void registerPets() {

        petMap.put("tiger", Tiger.class);
        petMap.put("penguin", Penguin.class);
        petMap.put("monkey", Monkey.class);
        petMap.put("skeleton", Skeleton.class);
        petMap.put("silverfish", Silverfish.class);
        petMap.put("fish", Fish.class);
        petMap.put("panda", Panda.class);

    }

}
