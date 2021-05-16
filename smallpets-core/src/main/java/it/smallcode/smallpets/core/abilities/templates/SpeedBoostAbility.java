package it.smallcode.smallpets.core.abilities.templates;
/*

Class created by SmallCode
19.10.2020 20:44

*/

public abstract class SpeedBoostAbility extends StatBoostAbility {

    private double speedBoostCap = 0;

    public SpeedBoostAbility(double maxExtraStat, double speedBoostCap, NumberDisplayType numberDisplayType) {

        this(maxExtraStat, 0, speedBoostCap, numberDisplayType);

    }

    public SpeedBoostAbility(double maxExtraStat, double minExtraStat, double speedBoostCap, NumberDisplayType numberDisplayType) {

        super(maxExtraStat, minExtraStat, numberDisplayType);

        this.speedBoostCap = speedBoostCap;

    }

    public double getSpeedBoostCap() {
        return speedBoostCap;
    }

}
