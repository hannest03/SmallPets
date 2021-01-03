package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
03.01.2021 11:20

*/

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

public interface IHealthModifierUtils {

    void addModifier(Player p, String name, double modifier, AttributeModifier.Operation operation);

    void removeModifier(Player p, String name);

}
