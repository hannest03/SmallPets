package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
04.09.2021 17:47

*/

import it.smallcode.smallpets.core.pets.experience.ExponentialGrowthFormula;
import it.smallcode.smallpets.core.pets.experience.GeometricGrowthFormula;
import it.smallcode.smallpets.core.pets.experience.LevelingFormula;

import java.util.HashMap;
import java.util.Map;

public class LevelingFormulaUtils {

    private static final Map<String, LevelingFormula> formulaMap = new HashMap<String, LevelingFormula>(){{
            put("geometric", new GeometricGrowthFormula());
            put("exponential", new ExponentialGrowthFormula());
        }};

    public static LevelingFormula loadLevelingFormula(Map<String, Object> data){
        LevelingFormula levelingFormula = formulaMap.values().iterator().next();
        String type = (String) data.get("type");
        if(type == null || !formulaMap.containsKey(type))
            return levelingFormula;
        levelingFormula = formulaMap.get(type);
        levelingFormula.load(data);
        return levelingFormula;
    }

}
