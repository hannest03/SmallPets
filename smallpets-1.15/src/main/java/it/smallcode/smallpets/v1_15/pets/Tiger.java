package it.smallcode.smallpets.v1_15.pets;
/*

Class created by SmallCode
02.07.2020 14:57

*/

import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.abilities.abilities.DamageAbility;
import it.smallcode.smallpets.core.conditions.BasicCondition;
import it.smallcode.smallpets.core.conditions.Condition;
import it.smallcode.smallpets.core.conditions.DateCondition;
import it.smallcode.smallpets.core.conditions.Operation;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetType;
import it.smallcode.smallpets.core.pets.Texture;
import it.smallcode.smallpets.core.pets.recipe.Recipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Tiger extends Pet {

    public Tiger() {
        super();

        super.setPetType(PetType.COMBAT);

        DamageAbility damageAbility = new DamageAbility();
        damageAbility.setAbilityType(AbilityType.STAT);
        damageAbility.setMaxStatBoost(20);
        damageAbility.setDescription("{{ability.damage_ability.description}}");
        super.getAbilities().add(damageAbility);

        ItemStack[] items = new ItemStack[9];
        items[1] = new ItemStack(Material.MUTTON);

        items[3] = new ItemStack(Material.CHICKEN);
        items[4] = new ItemStack(Material.BEEF);
        items[5] = new ItemStack(Material.RABBIT);

        items[7] = new ItemStack(Material.PORKCHOP);

        setRecipe(new Recipe(items));

        Texture normalTexture = new Texture();
        normalTexture.setPriority(2);
        normalTexture.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM0MjYzODc0NDkyMmI1ZmNmNjJjZDliZjI3ZWVhYjkxYjJlNzJkNmM3MGU4NmNjNWFhMzg4Mzk5M2U5ZDg0In19fQ==");

        BasicCondition basicCondition = new BasicCondition();
        basicCondition.setReturnValue(true);

        normalTexture.setConditions(new Condition[]{basicCondition});

        Texture christmasTexture = new Texture();
        christmasTexture.setPriority(1);
        christmasTexture.setTexture("ewogICJ0aW1lc3RhbXAiIDogMTYwNzY5NTQ4NjI3NSwKICAicHJvZmlsZUlkIiA6ICJmMzA1ZjA5NDI0NTg0ZjU4YmEyYjY0ZjAyZDcyNDYyYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJqcm9ja2EzMyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mYjc2ZjE2ZjZmNzBhNmQ4YWNkY2JjYzc4N2IwNWNiNGM1ZDUzMjE3M2FhZTNhNTc4OThjYTAzYTM0MDRmNDEiCiAgICB9CiAgfQp9");

        DateCondition firstDateCondition = new DateCondition();
        try {
            firstDateCondition.setDate(new SimpleDateFormat("dd/MM").parse("01/12"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        firstDateCondition.setOperation(Operation.GREATER_EQUALS);
        firstDateCondition.setReturnValue(true);

        DateCondition secondDateCondition = new DateCondition();
        try {
            secondDateCondition.setDate(new SimpleDateFormat("dd/MM").parse("25/12"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        secondDateCondition.setOperation(Operation.LESSER_EQUALS);
        secondDateCondition.setReturnValue(true);

        christmasTexture.setConditions(new Condition[]{firstDateCondition, secondDateCondition});

        setTextures(new Texture[]{normalTexture, christmasTexture});
    }
}
