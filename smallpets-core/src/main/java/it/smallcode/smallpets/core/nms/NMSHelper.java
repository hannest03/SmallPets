package it.smallcode.smallpets.core.nms;
/*

Class created by SmallCode
30.08.2021 10:45

*/

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class NMSHelper {

    private static final NMSHelper INSTANCE = new NMSHelper();

    private final String SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    private NMSHelper(){}

    public void sendPacket(Player player, Object packet){
        if(player == null)
            return;
        Object handle = getHandle(player);
        if(handle == null)
            return;
        try {
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    public Object getHandle(Player player){
        try {
            return player.getClass().getMethod("getHandle").invoke(player);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Class<?> getNMSClass(String name){
        try {
            return Class.forName("net.minecraft.server." + SERVER_VERSION + "." + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Class<?> getCraftBukkitClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + SERVER_VERSION + "." + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static NMSHelper getInstance() {
        return INSTANCE;
    }
}
