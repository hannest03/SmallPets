package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
20.07.2021 11:00

*/

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.conditions.Condition;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.Texture;
import it.smallcode.smallpets.core.pets.recipe.Recipe;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PetLoader {

    private static final String[] requiredElements = new String[]{
            "id",
            "namespace",
            "pettype",
            "default_translation",
            "particle",
            "abilities",
            "recipe",
            "textures"
    };

    public static Pet loadPet(JsonObject jsonObject){
        if(!validatePet(jsonObject))
            return null;

        Pet pet = new Pet();
        pet.setId(jsonObject.get("id").getAsString());
        pet.setNamespace(jsonObject.get("namespace").getAsString());
        pet.setPetType(PetType.valueOf(jsonObject.get("pettype").getAsString().toUpperCase(Locale.ROOT)));
        pet.setParticle(Particle.valueOf(jsonObject.get("particle").getAsString().toUpperCase(Locale.ROOT)));

        if(jsonObject.has("textures")){
            pet.setTextures(loadTexturesArray(jsonObject.get("textures").getAsJsonArray()));
        }

        String translationKey = "pet." + pet.getId();
        if(jsonObject.has("translation_key")){
            translationKey = jsonObject.get("translation_key").getAsString();
        }

        pet.setTranslationKey(translationKey);
        if(SmallPetsCommons.getSmallPetsCommons().getLanguageManager() != null && SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage() != null) {
            if(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getString(translationKey).equals("translations." + translationKey)) {
                SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getTranslations().put("translations." + translationKey, jsonObject.get("default_translation").getAsString());
            }
        }

        if(jsonObject.has("recipe")) {
            JsonObject recipeMap = jsonObject.getAsJsonObject("recipe");
            if(recipeMap.entrySet().size() != 0) {
                ItemStack[] itemStacks = new ItemStack[9];
                for (Map.Entry<String, JsonElement> entry : recipeMap.entrySet()) {
                    int index = Integer.parseInt(entry.getKey());
                    if (index < 0 || index >= itemStacks.length)
                        continue;
                    Map<String, Object> data = (Map<String, Object>) new Gson().fromJson(entry.getValue(), Map.class);
                    ItemStack itemStack = SmallPetsCommons.getSmallPetsCommons().getItemLoader().load(data);
                    itemStacks[index] = itemStack;
                }
                //TODO: Implement loading recipe
                Recipe recipe = new Recipe(itemStacks);
                pet.setRecipe(recipe);
            }
        }

        // Load abilities
        pet.setAbilities(loadAbilitiesList(jsonObject.get("abilities").getAsJsonArray()));

        return pet;
    }

    public static boolean validatePet(JsonObject jsonObject){
        for(String s : requiredElements){
            if(!jsonObject.has(s))
                return false;
        }

        try {
            PetType.valueOf(jsonObject.get("pettype").getAsString().toUpperCase(Locale.ROOT));
        }catch (Exception ex){
            return false;
        }

        try {
            Particle.valueOf(jsonObject.get("particle").getAsString().toUpperCase(Locale.ROOT));
        }catch (Exception ex){
            return false;
        }

        return true;
    }

    private static Texture[] loadTexturesArray(JsonArray array){
        Texture[] textures = new Texture[array.size()];
        for(int i = 0; i < array.size(); i++){
            Texture texture = loadTexture(array.get(i).getAsJsonObject());
            textures[i] = texture;
        }
        return textures;
    }

    private static Texture loadTexture(JsonObject object){
        Texture texture = new Texture();
        if(object.has("priority")){
            texture.setPriority(object.get("priority").getAsInt());
        }
        if(object.has("texture")){
            texture.setTexture(object.get("texture").getAsString());
        }
        if(object.has("conditions")){
            texture.setConditions(loadConditionsArray(object.get("conditions").getAsJsonArray()));
        }
        return texture;
    }

    private static Condition[] loadConditionsArray(JsonArray array){
        Condition[] conditions = new Condition[array.size()];
        for(int i = 0; i < array.size(); i++){
            Condition condition = loadCondition(array.get(i).getAsJsonObject());
            if(condition == null)
                continue;
            conditions[i] = condition;
        }
        return conditions;
    }

    private static Condition loadCondition(JsonObject object){
        if(!object.has("id"))
            return null;

        String id = object.get("id").getAsString();
        Condition condition = SmallPetsCommons.getSmallPetsCommons().getConditionsManager().getCondition(id);
        if(condition == null)
            return null;
        condition.load(object);
        return condition;
    }

    private static List<Ability> loadAbilitiesList(JsonArray array){
        List<Ability> abilities = new LinkedList<>();
        for(int i = 0; i < array.size(); i++){
            Ability ability = loadAbility(array.get(i).getAsJsonObject());
            if(ability != null)
                abilities.add(ability);
        }
        return abilities;
    }

    private static Ability loadAbility(JsonObject object){
        if(!object.has("id"))
            return null;

        String id = object.get("id").getAsString();
        Ability ability = SmallPetsCommons.getSmallPetsCommons().getAbilityManager().createAbility(id);
        if(ability == null)
            return null;
        ability.load(object);
        return ability;
    }

}
