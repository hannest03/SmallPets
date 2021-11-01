package it.smallcode.smallpets.core.pets.experience;
/*

Class created by SmallCode
04.09.2021 16:50

*/

import it.smallcode.smallpets.core.SmallPetsCommons;

import java.util.Map;

public class GeometricGrowthFormula implements LevelingFormula{

    private long expStart = 500;
    private double expMultiplier = 1.5;

    @Override
    public int getLevel(long exp) {
        return (int) Math.min(SmallPetsCommons.MAX_LEVEL, Math.max(SmallPetsCommons.MIN_LEVEL, logN(exp / (double) expStart, expMultiplier) + SmallPetsCommons.MIN_LEVEL +1));
    }

    @Override
    public long getExpForLevel(int level) {
        level = level -1;
        if(level <= 0)
            return 0;
        return (long) (expStart * Math.pow(expMultiplier, level-SmallPetsCommons.MIN_LEVEL));
    }

    @Override
    public void load(Map<String, Object> data) {
        if(data.containsKey("expStart")){
            this.expStart = Long.parseLong((String) data.get("expStart"));
        }
        if(data.containsKey("expMultiplier")){
            this.expMultiplier = (Double) data.get("expMultiplier");
        }
    }

    /**
        Calculates the log of a number.
        @param x - the number
        @param n - the base
     */
    private static double logN(double x, double n){
        return (Math.log(x) / Math.log(n));
    }

    public long getExpStart() {
        return expStart;
    }

    public void setExpStart(long expStart) {
        this.expStart = expStart;
    }

    public double getExpMultiplier() {
        return expMultiplier;
    }

    public void setExpMultiplier(double expMultiplier) {
        this.expMultiplier = expMultiplier;
    }
}
