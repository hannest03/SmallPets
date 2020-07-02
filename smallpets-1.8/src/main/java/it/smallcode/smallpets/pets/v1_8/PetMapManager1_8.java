package it.smallcode.smallpets.pets.v1_8;
/*

Class created by SmallCode
02.07.2020 15:15

*/

import it.smallcode.smallpets.pets.PetMapManager;

public class PetMapManager1_8 extends PetMapManager {

    protected void registerPets() {

        petMap.put("tiger", Tiger.class);

    }
}
