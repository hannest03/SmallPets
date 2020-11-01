package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
02.07.2020 15:15

*/

import it.smallcode.smallpets.core.manager.PetMapManager;
import it.smallcode.smallpets.v1_15.pets.*;

public class PetMapManager1_15 extends PetMapManager {

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
