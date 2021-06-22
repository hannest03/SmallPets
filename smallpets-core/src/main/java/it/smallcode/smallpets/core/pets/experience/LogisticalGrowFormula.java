package it.smallcode.smallpets.core.pets.experience;
/*

Class created by SmallCode
22.06.2021 21:17

*/

import it.smallcode.smallpets.core.SmallPetsCommons;

public class LogisticalGrowFormula implements LevelingFormula{

    private long xpToLevelTwo;
    private double tach = 0;

    public LogisticalGrowFormula(){
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

    public void setXpToLevelTwo(long xpToLevelTwo) {

        if(xpToLevelTwo > 0) {

           this.xpToLevelTwo = xpToLevelTwo;
            tach = -(Math.log(((SmallPetsCommons.MIN_LEVEL+1D) - (SmallPetsCommons.MAX_LEVEL +1D) ) / -((SmallPetsCommons.MAX_LEVEL +1D) - SmallPetsCommons.MIN_LEVEL)) / (double) xpToLevelTwo);

        }

    }
}
