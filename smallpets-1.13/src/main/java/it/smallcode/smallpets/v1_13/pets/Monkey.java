package it.smallcode.smallpets.v1_13.pets;
/*

Class created by SmallCode
03.01.2021 11:39

*/

import it.smallcode.smallpets.v1_15.abilities.standard.SpeedBoostInBiomeAbility;
import org.bukkit.block.Biome;

import java.util.Arrays;
import java.util.LinkedList;

public class Monkey extends it.smallcode.smallpets.v1_15.pets.Monkey {

    public Monkey() {
        super();
        super.getAbilities().clear();

        SpeedBoostInBiomeAbility speedBoostInBiomeAbility = new SpeedBoostInBiomeAbility();
        speedBoostInBiomeAbility.setBiomes(new LinkedList<>(Arrays.asList(Biome.JUNGLE, Biome.JUNGLE_HILLS, Biome.MODIFIED_JUNGLE)));
        speedBoostInBiomeAbility.setMinStatBoost(0.01D);
        speedBoostInBiomeAbility.setMaxStatBoost(0.05D);
        super.getAbilities().add(speedBoostInBiomeAbility);
    }
}
