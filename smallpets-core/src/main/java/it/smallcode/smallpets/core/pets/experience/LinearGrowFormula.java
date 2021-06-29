package it.smallcode.smallpets.core.pets.experience;
/*

Class created by SmallCode
22.06.2021 21:37

*/

import it.smallcode.smallpets.core.SmallPetsCommons;

public class LinearGrowFormula implements LevelingFormula {

    private long xpToLevelTwo = 500;

    @Override
    public int getLevel(long exp) {
        return (int) Math.min(SmallPetsCommons.MAX_LEVEL, (exp / xpToLevelTwo) + SmallPetsCommons.MIN_LEVEL);
    }

    @Override
    public long getExpForLevel(int level) {
        return (level-1) * xpToLevelTwo;
    }

    public void setXpToLevelTwo(long xpToLevelTwo) {
        this.xpToLevelTwo = xpToLevelTwo;
    }
}
