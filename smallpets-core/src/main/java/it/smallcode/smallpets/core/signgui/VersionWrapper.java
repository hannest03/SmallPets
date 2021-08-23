package it.smallcode.smallpets.core.signgui;
/*

Class created by SmallCode
20.08.2021 20:16

*/

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface VersionWrapper {

    void register(Plugin plugin);

    void openSign(Plugin plugin, Player player, String[] lines);

    void setOnSignClosedEvent(Player player, SignGUI.OnSignClosed onSignClosed);

}
