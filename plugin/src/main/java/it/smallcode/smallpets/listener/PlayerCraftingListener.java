package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
13.03.2022 16:34

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.recipe.Recipe;
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

        boolean correct = false;

        final Recipe[] recipes = pet.getRecipes();
        final ItemStack[] actualItems = e.getInventory().getMatrix();
        for(Recipe recipe : recipes) {
            final ItemStack[] items = recipe.getItems();
            if(correctRecipe(items, actualItems)) {
                correct = true;
                break;
            }
        }
        if(!correct){
            e.getInventory().setResult(null);
        }
    }

    private boolean correctRecipe(ItemStack[] expectedItems, ItemStack[] actualItems){
        for (int i = 0; i < 9; i++) {
            if (expectedItems[i] == null || actualItems[i] == null) {
                if ((expectedItems[i] != null && actualItems[i] == null) || (expectedItems[i] == null && actualItems[i] != null)) {
                    return false;
                }
                continue;
            }
            if (!expectedItems[i].equals(actualItems[i])) {
                return false;
            }
        }
        return true;
    }
}
