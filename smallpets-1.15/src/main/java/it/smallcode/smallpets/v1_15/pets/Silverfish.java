package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
01.11.2020 20:12

*/

import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import org.bukkit.entity.Player;

public class Silverfish extends Pet {

    public Silverfish(String id, Player owner, Long xp, Boolean useProtocolLib) {

        super(id, owner, xp, useProtocolLib);

        super.setPetType(PetType.mining);

        super.textureValue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE5MWRhYjgzOTFhZjVmZGE1NGFjZDJjMGIxOGZiZDgxOWI4NjVlMWE4ZjFkNjIzODEzZmE3NjFlOTI0NTQwIn19fQ==";

    }

}
