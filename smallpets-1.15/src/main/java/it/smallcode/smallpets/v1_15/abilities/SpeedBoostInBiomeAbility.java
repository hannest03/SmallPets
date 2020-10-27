package it.smallcode.smallpets.v1_15.abilities;
/*

Class created by SmallCode
26.10.2020 20:49

*/

import it.smallcode.smallpets.core.abilities.templates.InBiomeAbility;
import org.bukkit.block.Biome;

import java.util.LinkedList;
import java.util.List;

public class SpeedBoostInBiomeAbility extends InBiomeAbility {

    private double speed;

    public SpeedBoostInBiomeAbility(){

        super(new LinkedList<>());

        speed = 0;

    }

    public SpeedBoostInBiomeAbility(List<Biome> biome, double speed) {

        super(biome);

        this.speed = speed;

    }



}
