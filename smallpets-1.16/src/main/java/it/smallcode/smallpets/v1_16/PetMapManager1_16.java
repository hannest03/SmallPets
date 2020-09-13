package it.smallcode.smallpets.v1_16;
/*

Class created by SmallCode
05.07.2020 20:42

*/

import it.smallcode.smallpets.core.manager.PetMapManager;
import it.smallcode.smallpets.v1_16.pets.Monkey;
import it.smallcode.smallpets.v1_16.pets.Penguin;
import it.smallcode.smallpets.v1_16.pets.Tiger;

public class PetMapManager1_16 extends PetMapManager {


    @Override
    public void registerPets() {

        petMap.put("tiger", Tiger.class);
        petMap.put("penguin", Penguin.class);
        petMap.put("monkey", Monkey.class);

    }

}
