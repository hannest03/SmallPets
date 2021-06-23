package it.smallcode.smallpets.core.factory;
/*

Class created by SmallCode
19.06.2021 13:10

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.AbstractPet;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.entity.BukkitEntityHandler;
import it.smallcode.smallpets.core.pets.entity.ProtocolLibEntityHandler;
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
            Constructor constructor = clazz.getConstructor(String.class, UUID.class, Player.class, Long.class, Boolean.class);
            return (Pet) constructor.newInstance(type, uuid, owner, xp, useProtocollib);
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

    public static AbstractPet createPet(String type, UUID uuid, Player owner, Long xp){
        ImpPet pet = new ImpPet();
        pet.setId(type);
        pet.setUuid(uuid);
        pet.setOwner(owner);
        pet.setExp(xp);

        if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib())
            pet.setEntityHandler(new ProtocolLibEntityHandler());
        else
            pet.setEntityHandler(new BukkitEntityHandler());

        return pet;
    }

    private static class ImpPet extends AbstractPet{

        public ImpPet(){
            super();
            setPetType(PetType.combat);
        }

        @Override
        protected void updateTexture() {
            setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI3ZjY2M2Q2NWNkZWQ3YmQzNjUxYmRkZDZkYjU0NjM2MGRkNzczYWJiZGFmNDhiODNhZWUwOGUxY2JlMTQifX19");
        }

        @Override
        public void giveExp(long exp) {

        }
    }

}
