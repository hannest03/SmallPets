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

    private double maxExtraStat;

    public StatBoostAbility(double maxExtraStat){

        super(AbilityType.STAT);

        this.maxExtraStat = maxExtraStat;

    }

    @Override
    public List<String> getAbilityTooltip(Pet pet) {

        List<String> lore = new ArrayList<>();

        String description = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage()
                .getStringFormatted("ability." + getID() + ".description");

        description = description.replaceAll("%extraStat%", String.valueOf(DoubleFormater.maxDecimalPlaces(getExtraStat(pet.getLevel()), 2)));

        lore.add("§7" + description);

        return lore;

    }

    public double getExtraStat(double level){

        return (maxExtraStat / 100D) * level;

    }

}
