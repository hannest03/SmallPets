/*

Class created by SmallCode
22.06.2021 20:00

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.manager.PetMapManager;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.experience.LinearGrowFormula;
import it.smallcode.smallpets.core.pets.experience.LogisticalGrowFormula;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class PetTest {

    @Test
    public void testConstructor(){

        Pet pet = new Pet();

        assertEquals(true, pet != null);

        /*
        UUID uuid = UUID.randomUUID();
        Pet pet2 = PetFactory.createPet("tiger", uuid, null, 500L);

        assertEquals("tiger", pet2.getId());
        assertEquals(uuid, pet2.getUuid());
        assertEquals(null, pet2.getOwner());
        assertEquals(500L, pet2.getExp());
         */
    }

    @Test
    public void testAbilities(){
        Pet pet = new Pet();

        assertEquals(false, pet.hasAbility("abilityName"));

    }

    @Test
    public void testLevelingFormula(){

        //Test LogisticalGrowFormula
        SmallPetsCommons.getSmallPetsCommons().setLevelingFormula(new LogisticalGrowFormula());
        Pet pet = new Pet();
        assertEquals(1, pet.getLevel());
        assertEquals(500, pet.getExpForNextLevel());

        pet.setExp(500);

        assertEquals(2, pet.getLevel());

        //Change while running
        ((LogisticalGrowFormula)SmallPetsCommons.getSmallPetsCommons().getLevelingFormula()).setXpToLevelTwo(1000);
        assertEquals(1, pet.getLevel());
        assertEquals(1000, pet.getExpForNextLevel());

        pet.setExp(1000);

        assertEquals(2, pet.getLevel());

        //Test LinearGrowFormula
        SmallPetsCommons.getSmallPetsCommons().setLevelingFormula(new LinearGrowFormula());
        pet.setExp(0);
        assertEquals(1, pet.getLevel());
        assertEquals(500, pet.getExpForNextLevel());
        assertEquals(1000, SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(3));

        pet.setExp(500);

        assertEquals(2, pet.getLevel());

        //Change while running
        ((LinearGrowFormula)SmallPetsCommons.getSmallPetsCommons().getLevelingFormula()).setXpToLevelTwo(1000);
        assertEquals(1, pet.getLevel());
        assertEquals(1000, pet.getExpForNextLevel());
        assertEquals(2000, SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(3));

        pet.setExp(1000);

        assertEquals(2, pet.getLevel());

        pet.setExp(SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(100));
        assertEquals(100, pet.getLevel());

    }

}
