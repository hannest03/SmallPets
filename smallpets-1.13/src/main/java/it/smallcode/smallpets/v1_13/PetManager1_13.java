package it.smallcode.smallpets.v1_13;
/*

Class created by SmallCode
10.07.2020 15:05

*/

import it.smallcode.smallpets.v1_12.PetManager1_12;
import it.smallcode.smallpets.v1_12.pets.Fish;
import it.smallcode.smallpets.v1_12.pets.Panda;
import it.smallcode.smallpets.v1_15.pets.*;

public class PetManager1_13 extends PetManager1_12 {

    @Override
    public void registerPetClasses() {
        String namespace = "smallpets";

        registerPet(namespace, "tiger", Tiger.class);
        registerPet(namespace, "penguin", Penguin.class);
        registerPet(namespace, "monkey", Monkey.class);
        registerPet(namespace, "skeleton", Skeleton.class);
        registerPet(namespace, "silverfish", Silverfish.class);
        registerPet(namespace, "fish", Fish.class);
        registerPet(namespace, "panda", Panda.class);
    }

}
