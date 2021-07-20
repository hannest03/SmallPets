package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
20.07.2021 11:00

*/

import com.google.gson.JsonObject;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
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
        pet.setPetType(PetType.valueOf(jsonObject.get("pettype").getAsString().toUpperCase(Locale.ROOT)));
        pet.setParticle(Particle.valueOf(jsonObject.get("particle").getAsString().toUpperCase(Locale.ROOT)));

        //TODO: Add default translation and translation key

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

}
