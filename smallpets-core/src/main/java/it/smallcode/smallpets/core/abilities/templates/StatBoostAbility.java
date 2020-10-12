package it.smallcode.smallpets.core.abilities.templates;
/*

Class created by SmallCode
10.10.2020 18:14

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.utils.DoubleFormater;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class StatBoostAbility extends Ability {

    protected enum NumberDisplayType{

        INTEGER,
        TWO_DECIMAL_PLACES,
        FULL

    }

    private NumberDisplayType numberDisplayType;
    private double maxExtraStat;

    public StatBoostAbility(double maxExtraStat, NumberDisplayType numberDisplayType){

        super(AbilityType.STAT);

        this.maxExtraStat = maxExtraStat;
        this.numberDisplayType = numberDisplayType;

    }

    @Override
    public List<String> getAbilityTooltip(Pet pet) {

        List<String> lore = new ArrayList<>();

        String description = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage()
                .getStringFormatted("ability." + getID() + ".description");

        String value = "";

        if(numberDisplayType == NumberDisplayType.INTEGER){

            value = String.valueOf((int) getExtraStat(pet.getLevel()));

        }else if(numberDisplayType == NumberDisplayType.TWO_DECIMAL_PLACES){

            value = String.valueOf(DoubleFormater.maxDecimalPlaces(getExtraStat(pet.getLevel()), 2));

        }else if(numberDisplayType == NumberDisplayType.FULL){

            value = String.valueOf(getExtraStat(pet.getLevel()));

        }

        description = description.replaceAll("%extraStat%", value);

        lore.add("§7" + description);

        return lore;

    }

    public double getExtraStat(double level){

        return (maxExtraStat / 100D) * level;

    }

}
