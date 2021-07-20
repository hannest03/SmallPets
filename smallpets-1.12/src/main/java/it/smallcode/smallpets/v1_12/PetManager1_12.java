package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
09.07.2020 18:45

*/

import it.smallcode.smallpets.core.manager.PetManager;
import it.smallcode.smallpets.v1_12.pets.*;

public class PetManager1_12 extends PetManager {

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
