/*

Class created by SmallCode
22.06.2021 20:00

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.abilities.DamageAbility;
import it.smallcode.smallpets.core.abilities.abilities.HealthAbility;
import it.smallcode.smallpets.core.manager.AbilityManager;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.experience.GeometricGrowthFormula;
import it.smallcode.smallpets.core.pets.experience.ExponentialGrowthFormula;
import it.smallcode.smallpets.core.utils.FileUtils;
import it.smallcode.smallpets.core.utils.PetLoader;
import org.bukkit.Particle;
import org.junit.Test;

import java.io.File;

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
        SmallPetsCommons.getSmallPetsCommons().setLevelingFormula(new ExponentialGrowthFormula());
        Pet pet = new Pet();
        assertEquals(1, pet.getLevel());
        assertEquals(500, pet.getExpForNextLevel());

        pet.setExp(500);

        assertEquals(2, pet.getLevel());

        //Change while running
        ((ExponentialGrowthFormula)SmallPetsCommons.getSmallPetsCommons().getLevelingFormula()).setXpToLevelTwo(1000);
        assertEquals(1, pet.getLevel());
        assertEquals(1000, pet.getExpForNextLevel());

        pet.setExp(1000);

        assertEquals(2, pet.getLevel());

        //Test GeometricGrowthFormula
        SmallPetsCommons.getSmallPetsCommons().setLevelingFormula(new GeometricGrowthFormula());
        pet.setExp(0);
        assertEquals(1, pet.getLevel());
        assertEquals(500, pet.getExpForNextLevel());
        assertEquals(500, SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(2));
        assertEquals(750, SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(3));
        assertEquals(1125, SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(4));

        pet.setExp(500);
        assertEquals(2, pet.getLevel());

        pet.setExp(1125);
        assertEquals(4, pet.getLevel());

        pet.setExp(19221);
        assertEquals(10, pet.getLevel());
    }

    @Test
    public void testValidator(){
        String jsonString = "{ \"id\":\"test\" }";
        assertEquals(false, PetLoader.validatePet(FileUtils.loadToJson(jsonString)));

        jsonString = "{" +
                "\"id\":\"test\"," +
                "\"namespace\":\"smallpets\"," +
                "\"pettype\":\"COMBAT\"," +
                "\"default_translation\":\"test\"," +
                "\"particle\":\"VILLAGER_HAPPY\"," +
                "\"abilities\":[]," +
                "\"recipe\": []," +
                "\"textures\": []" +
                "}";
        assertEquals(true, PetLoader.validatePet(FileUtils.loadToJson(jsonString)));

        jsonString = "{" +
                "\"id\":\"test\"," +
                "\"namespace\":\"smallpets\"," +
                "\"pettype\":\"COMBAT\"," +
                "\"default_translation\":\"test\"," +
                "\"particle\":\"123\"," +
                "\"abilities\":[]," +
                "\"recipe\": []," +
                "\"textures\": []" +
                "}";
        assertEquals(false, PetLoader.validatePet(FileUtils.loadToJson(jsonString)));

        jsonString = "{" +
                "\"id\":\"test\"," +
                "\"namespace\":\"smallpets\"," +
                "\"pettype\":\"asd\"," +
                "\"default_translation\":\"test\"," +
                "\"particle\":\"VILLAGER_HAPPY\"," +
                "\"abilities\":[]," +
                "\"recipe\": []," +
                "\"textures\": []" +
                "}";
        assertEquals(false, PetLoader.validatePet(FileUtils.loadToJson(jsonString)));

        jsonString = "{" +
                "\"id\":\"test\"," +
                "\"namespace\":\"smallpets\"," +
                "\"pettype\":\"combat\"," +
                "\"default_translation\":\"test\"," +
                "\"particle\":\"VILLAGER_HAPPY\"," +
                "\"abilities\":[]," +
                "\"recipe\": []," +
                "\"textures\": []" +
                "}";
        assertEquals(true, PetLoader.validatePet(FileUtils.loadToJson(jsonString)));
    }

    @Test
    public void loadPetString() {
        final String jsonString = "{ \"id\":\"test\" }";
        assertEquals(null, PetLoader.loadPet(FileUtils.loadToJson(jsonString)));

        SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager() {
            @Override
            public void registerAbilities() {
                registerAbility("health_ability", HealthAbility.class);
                registerAbility("damage_ability", DamageAbility.class);
            }
        });

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("samplepet.json").getFile());
        Pet pet = PetLoader.loadPet(FileUtils.loadToJson(file));

        assertEquals("tiger", pet.getId());
        assertEquals(PetType.COMBAT, pet.getPetType());
        assertEquals(Particle.WATER_BUBBLE, pet.getParticle());

        File file1 = new File(classLoader.getResource("wrongsamplepet.json").getFile());
        Pet pet1 = PetLoader.loadPet(FileUtils.loadToJson(file1));
        assertEquals(null, pet1);
    }
}
