package it.smallcode.smallpets.v1_18;
/*

Class created by SmallCode
08.12.2021 16:29

*/

import it.smallcode.smallpets.v1_15.pets.*;
import it.smallcode.smallpets.v1_17.PetMapManager1_17;
import it.smallcode.smallpets.v1_18.pets.Monkey;

public class PetMapManager1_18 extends PetMapManager1_17 {

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
