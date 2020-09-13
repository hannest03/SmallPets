package it.smallcode.smallpets.core.pets;
/*

Class created by SmallCode
15.08.2020 10:28

*/

import it.smallcode.smallpets.core.languages.LanguageManager;

import java.util.Arrays;
import java.util.Optional;

public enum PetType {

    combat("combat"),
    mining("mining"),
    farming("farming"),
    fishing("fishing"),
    foraging("foraging");

    private String id;

    PetType(String id){

        this.id = id;

    }

    public String getId() {
        return id;
    }

    public String getName(LanguageManager languageManager){

        return languageManager.getLanguage().getStringFormatted("petType." + getId());

    }

    public static PetType fromID(String id){

        Optional<PetType> petTypeOptional = Arrays.stream(PetType.values()).filter(petType -> petType.getId().equalsIgnoreCase(id)).findFirst();

        if(petTypeOptional.isPresent()){

            return petTypeOptional.get();

        }

        return null;

    }

}
