package it.smallcode.smallpets.core.pets.entityHandler;
/*

Class created by SmallCode
23.06.2021 20:16

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BukkitEntityHandler implements EntityHandler{

    private ArmorStand armorStand;

    @Override
    public void spawn(Location location, ItemStack item) {
        armorStand = createArmorStand(location);

        //Please don't ask why

        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> armorStand.setCustomNameVisible(true), 1);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> armorStand.setGravity(false), 3);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> armorStand.setSmall(true), 4);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> armorStand.setInvulnerable(true), 5);

        armorStand.getEquipment().setHelmet(item);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> armorStand.setVisible(false), 6);
    }

    @Override
    public void spawnToPlayer(ItemStack item, Player player) {
        //Nothing to do here
    }

    @Override
    public void teleport(Location location) {
        if(armorStand != null){
            loadChunks(armorStand.getLocation());
            loadChunks(location);

            armorStand.teleport(location);
        }
    }

    @Override
    public void despawnFromPlayer(Player p) {
        //Nothing to do here
    }

    @Override
    public void destroy() {
        if(armorStand != null)
            armorStand.remove();
    }

    @Override
    public void setCustomName(String name) {
        if(armorStand == null)
            return;

        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(name);
    }

    @Override
    public boolean isEntity(int entityId) {
        return armorStand.getEntityId() == entityId;
    }

    private ArmorStand createArmorStand(Location loc){

        ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        return armorStand;

    }

    private void loadChunks(Location loc){

        if(!loc.getChunk().isLoaded())
            loc.getChunk().load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX()+1, loc.getChunk().getZ()).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX()+1, loc.getChunk().getZ()).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX()-1, loc.getChunk().getZ()).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX()-1, loc.getChunk().getZ()).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ()+1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ()+1).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ()-1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX(), loc.getChunk().getZ()-1).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX()+1, loc.getChunk().getZ()+1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX()+1, loc.getChunk().getZ()+1).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX()-1, loc.getChunk().getZ()-1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX()-1, loc.getChunk().getZ()-1).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX() -1, loc.getChunk().getZ() +1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX() -1, loc.getChunk().getZ() +1).load();

        if(!loc.getWorld().getChunkAt(loc.getChunk().getX() +1, loc.getChunk().getZ() -1).isLoaded())
            loc.getWorld().getChunkAt(loc.getChunk().getX() +1, loc.getChunk().getZ() -1).load();

    }

}
