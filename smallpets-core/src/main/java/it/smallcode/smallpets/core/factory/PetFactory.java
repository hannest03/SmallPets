package it.smallcode.smallpets.core.factory;
/*

Class created by SmallCode
19.06.2021 13:10

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class PetFactory {

    public static Pet createPet(String type, UUID uuid, Player owner, Long xp, Boolean useProtocollib){

        Class clazz = SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().get(type);
        if(clazz == null)
            return null;

        try {
            Constructor constructor = clazz.getConstructor(String.class, Player.class, Long.class, Boolean.class);
            Pet pet = (Pet) constructor.newInstance(type, owner, xp, useProtocollib);
            pet.setUuid(uuid);
            return pet;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Pet createNewPet(String type, Player owner, Long xp, Boolean useProtocollib){
        return createPet(type, UUID.randomUUID(), owner, xp, useProtocollib);
    }

}
