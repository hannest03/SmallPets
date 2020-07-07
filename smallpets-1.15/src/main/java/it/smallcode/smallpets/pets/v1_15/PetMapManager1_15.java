package it.smallcode.smallpets.pets.v1_15;
/*

Class created by SmallCode
02.07.2020 15:15

*/

import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.pets.v1_15.pets.Monkey;
import it.smallcode.smallpets.pets.v1_15.pets.Penguin;
import it.smallcode.smallpets.pets.v1_15.pets.Tiger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public class PetMapManager1_15 extends PetMapManager {

    public void registerPets() {

        petMap.put("tiger", Tiger.class);
        petMap.put("penguin", Penguin.class);
        petMap.put("monkey", Monkey.class);

    }

    public void registerCraftingRecipe(Plugin plugin){

        petMap.values().iterator().forEachRemaining(new Consumer<Class>() {
            @Override
            public void accept(Class aClass) {

                try {

                    Constructor constructor = aClass.getConstructor(Player.class, Long.class);

                    Pet pet = (Pet) constructor.newInstance(null, 0L);

                    pet.registerRecipe(plugin);

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
        });

    }

}
