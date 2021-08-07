package it.smallcode.smallpets.core.abilities.templates;
/*

Class created by SmallCode
05.08.2021 16:43

*/

import com.google.gson.JsonObject;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.utils.DoubleFormater;
import it.smallcode.smallpets.core.utils.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
public abstract class StatBoostAbility extends Ability {

    protected enum NumberDisplayType{
        INTEGER,
        TWO_DECIMAL_PLACES,
        FULL
    }

    private NumberDisplayType numberDisplayType = NumberDisplayType.TWO_DECIMAL_PLACES;
    private double maxStatBoost = 0;
    private double minStatBoost = 0;

    @Override
    public void load(JsonObject jsonObject) {
        super.load(jsonObject);
        if(jsonObject.has("max_stat_boost")){
            maxStatBoost = jsonObject.get("max_stat_boost").getAsDouble();
        }
        if(jsonObject.has("min_stat_boost")){
            minStatBoost = jsonObject.get("min_stat_boost").getAsDouble();
        }
        if(jsonObject.has("numberDisplayType")){
            this.numberDisplayType = NumberDisplayType.valueOf(jsonObject.get("numberDisplayType").getAsString().toUpperCase(Locale.ROOT));
        }
    }

    @Override
    public List<String> getAbilityTooltip(Pet pet) {
        List<String> lore = new ArrayList<>();
        if(getAbilityType() == AbilityType.ABILITY)
            lore.add("§6" + getName());
        String description = getDescription();

        String value = "";
        if(numberDisplayType == NumberDisplayType.INTEGER){
            value = String.valueOf((int) getStatBoost(pet.getLevel()));
        }else if(numberDisplayType == NumberDisplayType.TWO_DECIMAL_PLACES){
            value = String.valueOf(DoubleFormater.maxDecimalPlaces(getStatBoost(pet.getLevel()), 2));
        }else if(numberDisplayType == NumberDisplayType.FULL){
            value = String.valueOf(getStatBoost(pet.getLevel()));
        }

        description = description.replaceAll("%extra_stat%", value);

        description = StringUtils.addLineBreaks(description, 30);

        for(String s : description.split("\n"))
            lore.add("§7" + s);

        return lore;

    }

    public double getStatBoost(double level){
        return ((maxStatBoost-minStatBoost) / (SmallPetsCommons.MAX_LEVEL-1)) * (level-1) + minStatBoost;
    }

}
