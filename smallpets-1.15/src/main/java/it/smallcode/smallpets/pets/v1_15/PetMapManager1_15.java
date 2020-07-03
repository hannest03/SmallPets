package it.smallcode.smallpets.pets.v1_15;
/*

Class created by SmallCode
02.07.2020 15:15

*/

import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.pets.v1_15.pets.Penguin;
import it.smallcode.smallpets.pets.v1_15.pets.Tiger;

public class PetMapManager1_15 extends PetMapManager {

    protected void registerPets() {

        petMap.put("tiger", Tiger.class);
        petMap.put("penguin", Penguin.class);

    }

}
