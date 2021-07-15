package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
14.07.2021 21:22

*/

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

public interface ISpeedModifierUtils {

    void addModifier(Player p, String name, double modifier, AttributeModifier.Operation operation);

    void removeModifier(Player p, String name);

}
