package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
02.07.2020 15:15

*/

import it.smallcode.smallpets.core.manager.PetManager;
import it.smallcode.smallpets.v1_15.pets.*;

public class PetManager1_15 extends PetManager {

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
