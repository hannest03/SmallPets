package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
20.07.2021 11:00

*/

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.conditions.Condition;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.Texture;
import org.bukkit.Particle;

import java.util.Locale;

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
            System.out.println("-------------");
            Texture texture = loadTexture(array.get(i).getAsJsonObject());
            textures[i] = texture;
        }
        return textures;
    }

    private static Texture loadTexture(JsonObject object){
        Texture texture = new Texture();
        if(object.has("priority")){
            texture.setPriority(object.get("priority").getAsInt());
            System.out.println(texture.getPriority());
        }
        if(object.has("texture")){
            texture.setTexture(object.get("texture").getAsString());
            System.out.println(texture.getTexture());
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
        System.out.println(id);
        Condition condition = SmallPetsCommons.getSmallPetsCommons().getConditionsManager().getCondition(id);
        if(condition == null)
            return null;
        condition.load(object);
        return condition;
    }

}
