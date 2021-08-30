package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
30.08.2021 13:29

*/

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.nms.NMSHelper;
import it.smallcode.smallpets.core.signgui.SignGUI;
import it.smallcode.smallpets.core.signgui.VersionWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignGUIWrapper1_15 implements VersionWrapper, Listener {

    private static Listener listener = null;

    private final Map<UUID, SignGUI.OnSignClosed> onSignClosedMap = new HashMap<>();

    private HashMap<String, Field> fieldCache = new HashMap<>();

    @Override
    public void register(Plugin plugin) {
        if(listener == null){
            listener = new SignGUIWrapper1_15();
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }

    @Override
    public void openSign(Plugin plugin, Player player, String[] lines) {

        Location location = player.getLocation();
        location.setY(5);

        Block block = location.getWorld().getBlockAt(location);
        Material material = block.getType();
        if(!(block.getState() instanceof Sign)) {
            block.setType(Material.OAK_SIGN);
        }

        //Check for finished editing sign
        addChannelPipe(player);

        //Wait for the block to get updated
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                Sign sign = (Sign) block.getState();
                sign.setEditable(true);
                for(int i = 0; i < sign.getLines().length; i++){
                    sign.setLine(i, lines[i]);
                }
                sign.update();
                //Wait for the sign to get updated
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        Block block = sign.getBlock();

                        Object position;
                        try {
                            Class clazz = NMSHelper.getInstance().getNMSClass("BlockPosition");
                            Constructor constructor = clazz.getConstructor(int.class, int.class, int.class);
                            position = constructor.newInstance(block.getX(), block.getY(), block.getZ());
                        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
                            ex.printStackTrace();
                            return;
                        }
                        Object packet;
                        try {
                            Class clazz = NMSHelper.getInstance().getNMSClass("PacketPlayOutOpenSignEditor");
                            Constructor constructor = clazz.getConstructor(NMSHelper.getInstance().getNMSClass("BlockPosition"));
                            packet = constructor.newInstance(position);
                        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
                            ex.printStackTrace();
                            return;
                        }
                        NMSHelper.getInstance().sendPacket(player, packet);
                        //Wait for the packet to get sent
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                block.setType(material);
                            }
                        }, 1L);
                    }
                }, 1L);
            }
        }, 1L);
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e){
        removeChannelPipe(e.getPlayer());
        onSignClosedMap.remove(e.getPlayer().getUniqueId());
    }

    private void addChannelPipe(Player p){
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                Class clazz = NMSHelper.getInstance().getNMSClass("PacketPlayInUpdateSign");
                if (msg.getClass() == clazz) {
                    Object packet = msg;
                    if (onSignClosedMap.containsKey(p.getUniqueId())) {
                        String[] lines;

                        Method method = packet.getClass().getMethod("c");
                        method.setAccessible(true);
                        lines = (String[]) method.invoke(packet);
                        method.setAccessible(false);

                        onSignClosedMap.get(p.getUniqueId()).onSignClosed(lines);
                        onSignClosedMap.remove(p.getUniqueId());
                    }
                    removeChannelPipe(p);
                }
                super.channelRead(ctx, msg);
            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> {
            Object handle = NMSHelper.getInstance().getHandle(p);

            Object playerConnection = getField(handle, "playerConnection");
            Object networkManager = getField(playerConnection, "networkManager");
            Channel channel = (Channel) getField(networkManager, "channel");

            ChannelPipeline pipeline = channel.pipeline();
            if(pipeline.get(p.getName() + "_signgui") != null)
                return;
            pipeline.addBefore("packet_handler", p.getName() + "_signgui", channelDuplexHandler);
        }, 10L);
    }

    private void removeChannelPipe(Player p){
        Object handle = NMSHelper.getInstance().getHandle(p);

        Object playerConnection = getField(handle, "playerConnection");
        Object networkManager = getField(playerConnection, "networkManager");
        Channel channel = (Channel) getField(networkManager, "channel");

        ChannelPipeline pipeline = channel.pipeline();
        if(pipeline.get(p.getName() + "_signgui") == null)
            return;
        pipeline.remove(p.getName() + "_signgui");
    }

    @Override
    public void setOnSignClosedEvent(Player player, SignGUI.OnSignClosed onSignClosed) {
        onSignClosedMap.put(player.getUniqueId(), onSignClosed);
    }

    private Object getField(Object obj, String fieldName) {
        Object ret = null;

        if(!fieldCache.containsKey(fieldName)){
            try {
                Field field = obj.getClass().getField(fieldName);
                fieldCache.put(fieldName, field);
            } catch (NoSuchFieldException ex) {
                ex.printStackTrace();
            }
        }
        try {
            Field field = fieldCache.get(fieldName);
            field.setAccessible(true);
            ret = field.get(obj);
            field.setAccessible(false);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return ret;
    }



}
