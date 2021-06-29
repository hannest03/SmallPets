package it.smallcode.smallpets.core.pets.experience;
/*

Class created by SmallCode
22.06.2021 21:14

*/

public interface LevelingFormula {

    int getLevel(long exp);
    long getExpForLevel(int level);

}
