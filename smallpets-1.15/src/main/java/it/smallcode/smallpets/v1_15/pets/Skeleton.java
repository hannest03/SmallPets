package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
01.11.2020 19:05

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.v1_15.abilities.DontConsumeArrowAbility;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Skeleton extends Pet {

    public Skeleton(String id, Player owner, Long xp, Boolean useProtocolLib) {

        super(id, owner, xp, useProtocolLib);

        super.setPetType(PetType.combat);
        super.setParticle(Particle.CLOUD);

        super.abilities.add(new DontConsumeArrowAbility(10, 1));

        super.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxMjY4ZTljNDkyZGExZjBkODgyNzFjYjQ5MmE0YjMwMjM5NWY1MTVhN2JiZjc3ZjRhMjBiOTVmYzAyZWIyIn19fQ==";

    }
}
