package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
14.07.2021 21:23

*/

import it.smallcode.smallpets.core.utils.ISpeedModifierUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

public class SpeedModifierUtils1_15 implements ISpeedModifierUtils {

    @Override
    public void addModifier(Player p, String name, double modifier, AttributeModifier.Operation operation) {
        if(p != null){
            AttributeInstance baseSpeedAttrib = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            if(baseSpeedAttrib != null){
                baseSpeedAttrib.addModifier(new AttributeModifier(name, modifier, operation));
            }
        }
    }

    @Override
    public void removeModifier(Player p, String name) {
        if(p != null){
            AttributeInstance baseHealthAttrib = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
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
