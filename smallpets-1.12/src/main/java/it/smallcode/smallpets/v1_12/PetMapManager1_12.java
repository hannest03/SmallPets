package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
09.07.2020 18:45

*/

import com.comphenix.protocol.PacketType;
import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.v1_12.pets.Monkey;
import it.smallcode.smallpets.v1_12.pets.Penguin;
import it.smallcode.smallpets.v1_12.pets.Tiger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public class PetMapManager1_12 extends PetMapManager {


    @Override
    public void registerPets() {

        petMap.put("tiger", Tiger.class);
        petMap.put("penguin", Penguin.class);
        petMap.put("monkey", Monkey.class);

    }

    @Override
    public void registerCraftingRecipe(Plugin plugin) {

        petMap.values().iterator().forEachRemaining(new Consumer<Class>() {
            @Override
            public void accept(Class aClass) {

                try {

                    Constructor constructor = aClass.getConstructor(Player.class, Long.class, Boolean.class);

                    Pet pet = (Pet) constructor.newInstance(null, 0L, false);

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
