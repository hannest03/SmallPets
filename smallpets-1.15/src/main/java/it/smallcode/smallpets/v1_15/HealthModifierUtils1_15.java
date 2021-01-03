package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
03.01.2021 12:07

*/

import it.smallcode.smallpets.core.utils.IHealthModifierUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

public class HealthModifierUtils1_15 implements IHealthModifierUtils {

    @Override
    public void addModifier(Player p, String name, double modifier, AttributeModifier.Operation operation) {

        if(p != null){

            AttributeInstance baseHealthAttrib = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);

            if(baseHealthAttrib != null){

                baseHealthAttrib.addModifier(new AttributeModifier(name, modifier, operation));

            }

        }

    }

    @Override
    public void removeModifier(Player p, String name) {

        if(p != null){

            AttributeInstance baseHealthAttrib = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);

            if(baseHealthAttrib != null){

                baseHealthAttrib.getModifiers().stream().filter(
                        attributeModifier -> attributeModifier.getName().equals(name)
                ).forEach(
                        baseHealthAttrib::removeModifier
                );

            }

        }

    }
}
