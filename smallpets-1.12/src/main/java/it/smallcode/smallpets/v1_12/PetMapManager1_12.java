package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
09.07.2020 18:45

*/

import it.smallcode.smallpets.core.manager.PetMapManager;
import it.smallcode.smallpets.v1_12.pets.Monkey;
import it.smallcode.smallpets.v1_12.pets.Penguin;
import it.smallcode.smallpets.v1_12.pets.Tiger;

public class PetMapManager1_12 extends PetMapManager {


    @Override
    public void registerPets() {

        petMap.put("tiger", Tiger.class);
        petMap.put("penguin", Penguin.class);
        petMap.put("monkey", Monkey.class);

    }
}
