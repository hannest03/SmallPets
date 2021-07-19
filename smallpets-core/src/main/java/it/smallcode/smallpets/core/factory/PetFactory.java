package it.smallcode.smallpets.core.factory;
/*

Class created by SmallCode
19.06.2021 13:10

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.entityHandler.BukkitEntityHandler;
import it.smallcode.smallpets.core.pets.entityHandler.ProtocolLibEntityHandler;
import it.smallcode.smallpets.core.pets.logic.BasicLogic;
import it.smallcode.smallpets.core.pets.progressbar.DefaultProgressbar;
import it.smallcode.smallpets.core.pets.progressbar.PercentageProgressbar;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class PetFactory {

    public static Pet createPet(String id, UUID uuid, Player owner, Long exp) {

        //PLACEHOLDER(!) WILL BE CHANGED

        Class clazz = SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().get(id);
        if(clazz == null)
            return null;

        try {
            Constructor constructor = clazz.getConstructor();
            Pet pet = (Pet) constructor.newInstance();
            pet.setId(id);
            pet.setUuid(uuid);
            pet.setOwner(owner);
            pet.setExp(exp);
            pet.setLogic(new BasicLogic());

            if (SmallPetsCommons.getSmallPetsCommons().isUseProtocollib())
                pet.setEntityHandler(new ProtocolLibEntityHandler());
            else
                pet.setEntityHandler(new BukkitEntityHandler());

            return pet;

        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        //TODO: Check if is config pet or class

        return null;
    }

    public static Pet createNewPet(String type, Player owner, Long xp){
        return createPet(type, UUID.randomUUID(), owner, xp);
    }

}
