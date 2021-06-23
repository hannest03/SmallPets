package it.smallcode.smallpets.core.pets.entity;
/*

Class created by SmallCode
23.06.2021 20:29

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ProtocolLibEntityHandler implements EntityHandler{

    private static Set<Integer> entityIds = new HashSet<>();

    private int entityId;
    private Location location;

    @Override
    public void spawn(Location location, ItemStack item) {
        do{
            entityId = (int) (Math.random() * 10000);
        }while(entityIds.contains(entityId));
        this.location = location;
        List<Player> players = getPlayersWithVisibleActivated(location);
        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> spawnArmorstandWithPackets(location, item, players));
        entityIds.add(entityId);
    }

    @Override
    public void spawnToPlayer(Location location, ItemStack item, Player player) {
        this.location = location;
        List<Player> players = new LinkedList<>(Arrays.asList(player));
        spawnArmorstandWithPackets(location, item, players);
    }

    @Override
    public void teleport(Location location) {
        this.location = location;
        PacketContainer teleportPacket = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().teleportEntity(entityId, location);
        sendPacket(getPlayersWithVisibleActivated(location), teleportPacket);
    }

    @Override
    public void despawnFromPlayer(Player p) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> {
            PacketContainer entityDestroy = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);

            int[] entityIDs = new int[1];
            entityIDs[0] = entityId;

            entityDestroy.getIntegerArrays().write(0, entityIDs);
            if(p != null) {
                sendPacket(entityDestroy, p);
            }
        });
    }

    @Override
    public void destroy() {
        PacketContainer entityDestroy = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().destroyEntity(entityId);
        if(location != null) {
            sendPacket(getPlayersWithVisibleActivated(location), entityDestroy);
        }
    }

    @Override
    public void setCustomName(String name) {
        System.out.println(entityId);
        System.out.println(name);
        PacketContainer entityMetadata = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().setCustomName(entityId, name);
        System.out.println(entityMetadata.toString());
        sendPacket(getPlayersWithVisibleActivated(location), entityMetadata);
    }

    private void spawnArmorstandWithPackets(Location location, ItemStack item, List<Player> players) {

        //SPAWN ARMORSTAND

        PacketContainer spawnPacket = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().spawnArmorstand(entityId, location);

        //EQUIPMENT

        PacketContainer entityEquipment = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().equipItem(entityId, EnumWrappers.ItemSlot.HEAD, item);

        //METADATA

        PacketContainer entityMetadata = SmallPetsCommons.getSmallPetsCommons().getProtocolLibUtils().standardMetaData(entityId);

        sendPacket(players, spawnPacket);
        sendPacket(players, entityEquipment);
        sendPacket(players, entityMetadata);

    }

    private List<Player> getPlayersWithVisibleActivated(Location location){

        List<Player> players = new LinkedList<>();

        for(Player all : Bukkit.getOnlinePlayers())
            if(all.getWorld().getName().equals(location.getWorld().getName())){

                User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(all.getUniqueId().toString());

                if(user != null && user.getSettings().isShowPets()) {

                    players.add(all);

                }

            }

        return players;

    }

    private void sendPacket(List<Player> players, PacketContainer packetContainer){

        for(Player player : players){

            sendPacket(packetContainer, player);

        }

    }

    private void sendPacket(PacketContainer packetContainer, Player player){
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

}
