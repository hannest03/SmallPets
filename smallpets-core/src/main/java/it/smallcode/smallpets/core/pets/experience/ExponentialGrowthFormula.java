package it.smallcode.smallpets.core.pets.experience;
/*

Class created by SmallCode
22.06.2021 21:17

*/

import it.smallcode.smallpets.core.SmallPetsCommons;

import java.util.Map;

public class ExponentialGrowthFormula implements LevelingFormula{

    private double tach = 0;

    public ExponentialGrowthFormula(){
        setXpToLevelTwo(500);
    }

    @Override
    public int getLevel(long exp) {
        return (int) ((SmallPetsCommons.MAX_LEVEL) * (1 - Math.pow(Math.E, -tach * exp)) + SmallPetsCommons.MIN_LEVEL);
    }

    @Override
    public long getExpForLevel(int level) {
        return (long) (Math.log(((level) - (SmallPetsCommons.MAX_LEVEL +1D)) / -((SmallPetsCommons.MAX_LEVEL +1D) - SmallPetsCommons.MIN_LEVEL)) / -tach);
    }

    @Override
    public void load(Map<String, Object> data) {
        if(data.containsKey("expNextLevel")){
            setXpToLevelTwo(Long.parseLong((String) data.get("expNextLevel")));
        }
    }

    public void setXpToLevelTwo(long xpToLevelTwo) {
        if(xpToLevelTwo > 0) {
            tach = -(Math.log(((SmallPetsCommons.MIN_LEVEL+1D) - (SmallPetsCommons.MAX_LEVEL +1D) ) / -((SmallPetsCommons.MAX_LEVEL +1D) - SmallPetsCommons.MIN_LEVEL)) / (double) xpToLevelTwo);
        }
    }
}
