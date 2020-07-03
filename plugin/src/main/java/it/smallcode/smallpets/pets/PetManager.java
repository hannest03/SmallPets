package it.smallcode.smallpets.pets;
/*

Class created by SmallCode
02.07.2020 15:27

*/

import it.smallcode.smallpets.SmallPets;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PetManager {

    private HashMap<Player, Pet> pets;

    public PetManager(){

        pets = new HashMap<>();

    }

    public void spawnPet(String type, Player owner, int exp){

        Pet pet = createPet(type, owner, exp);

        pet.spawn(SmallPets.getInstance());

        pets.put(owner, pet);

    }

    public void despawnPet(Player p){

        if(pets.containsKey(p)){

            pets.get(p).destroy();

            pets.remove(p);

        }

    }

    public void despawnAll(){

        for(int i = pets.size() -1; i >= 0; i--){

            despawnPet((Player) pets.keySet().toArray()[i]);

        }

    }

    private Pet createPet(String type, Player owner, int exp){

        if(SmallPets.getInstance().getPetMapManager().petMap.containsKey(type)){

            try {

                Constructor constructor = SmallPets.getInstance().getPetMapManager().petMap.get(type).getConstructor(Player.class, Integer.class);

                return (Pet) constructor.newInstance(owner, exp);

            } catch (NoSuchMethodException ex) {

                ex.printStackTrace();

            } catch (IllegalAccessException ex) {

                ex.printStackTrace();

            } catch (InstantiationException ex) {

                ex.printStackTrace();

            } catch (InvocationTargetException ex) {

                ex.printStackTrace();

            }

        }

        return null;

    }

}
