package it.smallcode.smallpets.core.factory;
/*

Class created by SmallCode
19.06.2021 13:10

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.PetManager;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.entityHandler.BukkitEntityHandler;
import it.smallcode.smallpets.core.pets.entityHandler.ProtocolLibEntityHandler;
import it.smallcode.smallpets.core.pets.logic.BasicLogic;
import it.smallcode.smallpets.core.pets.progressbar.DefaultProgressbar;
import it.smallcode.smallpets.core.pets.progressbar.PercentageProgressbar;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.UUID;

public class PetFactory {

    public static Pet createPet(String namespace, String id, UUID uuid, Player owner, Long exp){
        Pet pet = null;
        Object object = SmallPetsCommons.getSmallPetsCommons().getPetManager().getPet(namespace, id);
        if(object != null)
            if(object.getClass() == Class.class){
                try {
                    Class clazz = (Class) object;
                    Constructor constructor = clazz.getConstructor();
                    pet = (Pet) constructor.newInstance();
                    pet.setId(id);
                    pet.setNamespace(namespace);
                    if(namespace == null)
                        pet.setNamespace(SmallPetsCommons.getSmallPetsCommons().getPetManager().getPetNamespace(id));
                    pet.setTranslationKey("pet." + pet.getId());
                } catch (NoSuchMethodException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }else {
                pet = ((Pet) SmallPetsCommons.getSmallPetsCommons().getPetManager().getPet(namespace, id)).clone();
            }

        if(pet == null)
            return null;

        pet.setUuid(uuid);
        pet.setOwner(owner);
        pet.setExp(exp);
        pet.setLogic(new BasicLogic());

        if (SmallPetsCommons.getSmallPetsCommons().isUseProtocollib())
            pet.setEntityHandler(new ProtocolLibEntityHandler());
        else
            pet.setEntityHandler(new BukkitEntityHandler());

        return pet;
    }

    public static Pet createPet(String id, UUID uuid, Player owner, Long exp) {
        return createPet(null, id, uuid, owner, exp);
    }

    public static Pet createNewPet(String type, Player owner, Long xp){
        return createPet(type, UUID.randomUUID(), owner, xp);
    }

    public static Pet createNewPet(String namespace, String id, Player owner, Long exp){
        return createPet(namespace, id, UUID.randomUUID(), owner, exp);
    }

}
