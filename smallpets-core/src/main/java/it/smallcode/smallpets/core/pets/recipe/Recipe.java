package it.smallcode.smallpets.core.pets.recipe;
/*

Class created by SmallCode
18.07.2021 18:18

*/

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Recipe {

    @Getter
    private ItemStack[] items;

    public Recipe(ItemStack[] items){
        this.items = items;
    }

    public ShapedRecipe fillShape(ShapedRecipe recipe){
        Map<ItemStack, Character> itemMap = new HashMap<>();
        itemMap.put(null, ' ');
        char next = 'a';
        String[] shape = new String[3];
        int counter = 0;
        for(ItemStack item : items){
            if(counter % 3 == 0)
                shape[counter/3] = "";
            if(!itemMap.containsKey(item)){
                itemMap.put(item, next);
                next++;
            }
            shape[counter/3] += itemMap.get(item);
            counter++;
        }
        recipe.shape(shape);
        for(ItemStack item : itemMap.keySet()){
            if(item == null)
                continue;
            RecipeChoice recipeChoice = new RecipeChoice.ExactChoice(item);
            recipe.setIngredient(itemMap.get(item), recipeChoice);
        }
        return recipe;
    }

}
