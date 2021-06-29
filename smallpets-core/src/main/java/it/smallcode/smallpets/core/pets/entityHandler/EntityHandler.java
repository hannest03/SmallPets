package it.smallcode.smallpets.core.pets.entityHandler;
/*

Class created by SmallCode
23.06.2021 20:12

*/

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface EntityHandler {

    void spawn(Location location, ItemStack item);
    void spawnToPlayer(ItemStack item, Player player);
    void teleport(Location location);
    void despawnFromPlayer(Player p);
    void destroy();

    void setCustomName(String name);

}
