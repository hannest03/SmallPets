package it.smallcode.smallpets.core.abilities.templates;
/*

Class created by SmallCode
10.10.2020 18:14

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.utils.DoubleFormater;
import it.smallcode.smallpets.core.utils.StringUtils;
import org.bukkit.entity.Player;

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
    private double minExtraStat;

    public StatBoostAbility(double maxExtraStat, NumberDisplayType numberDisplayType){

        this(maxExtraStat, 0, numberDisplayType);

    }

    public StatBoostAbility(double maxExtraStat, double minExtraStat, NumberDisplayType numberDisplayType){

        super(AbilityType.STAT);

        this.maxExtraStat = maxExtraStat;
        this.minExtraStat = minExtraStat;
        this.numberDisplayType = numberDisplayType;

    }

    @Override
    public List<String> getAbilityTooltip(Pet pet) {

        List<String> lore = new ArrayList<>();

        if(getAbilityType() == AbilityType.ABILITY)
            lore.add("§6" + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("ability." + getID() + ".name"));

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

        description = StringUtils.addLineBreaks(description, 30);

        for(String s : description.split("\n"))
            lore.add("§7" + s);

        return lore;

    }

    public abstract void addBoost(Player p, Ability ability);
    public abstract void removeBoost(Player p, Ability ability);

    public double getExtraStat(double level){

        return ((maxExtraStat-minExtraStat) / (Pet.MAXLEVEL-1)) * (level-1) + minExtraStat;

    }

}
