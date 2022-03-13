package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
13.03.2022 16:34

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerCraftingListener implements Listener {
    @EventHandler
    public void onPlayerCraftItem(PrepareItemCraftEvent e){
        if(e.getInventory().getMatrix().length < 9){
            return;
        }
        if(e.getRecipe() == null) return;
        final ItemStack result = e.getRecipe().getResult();
        if(!SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(result, "pet")){
            return;
        }

        final String id = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().getNBTTagValue(result, "pet");
        Pet pet;
        if(SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().hasNBTTag(result, "pet.namespace")){
            final String namespace = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().getNBTTagValue(result, "pet.namespace");
            pet = PetFactory.createNewPet(namespace, id, null, 0L);
        }else{
            pet = PetFactory.createNewPet(id, null, 0L);
        }

        boolean correct = true;

        final ItemStack[] items = pet.getRecipe().getItems();
        for(int i = 0; i < 9; i++) {
            if(items[i] == null || e.getInventory().getMatrix()[i] == null){
                if((items[i] != null && e.getInventory().getMatrix()[i] == null) || (items[i] == null && e.getInventory().getMatrix()[i] != null)){
                    correct = false;
                }
                continue;
            }
            if(!items[i].equals(e.getInventory().getMatrix()[i])){
                correct = false;
            }
        }
        if(!correct){
            e.getInventory().setResult(null);
        }else{
            e.getInventory().setResult(result);
        }
    }
}
