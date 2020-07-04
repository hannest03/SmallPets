package it.smallcode.smallpets.manager;
/*

Class created by SmallCode
02.07.2020 15:11

*/

import java.util.HashMap;

public abstract class PetMapManager {

    protected HashMap<String, Class> petMap;

    public PetMapManager(){

        petMap = new HashMap<String, Class>();

        registerPets();

    }

    public HashMap<String, Class> getPetMap() {
        return petMap;
    }

    protected abstract void registerPets();

}
