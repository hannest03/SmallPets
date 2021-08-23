package it.smallcode.smallpets.v1_17;
/*

Class created by SmallCode
20.08.2021 20:18

*/

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.signgui.SignGUI;
import it.smallcode.smallpets.core.signgui.VersionWrapper;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignGUIWrapper1_17 implements VersionWrapper, Listener {

    private static Listener listener = null;

    private final Map<UUID, SignGUI.OnSignClosed> onSignClosedMap = new HashMap<>();

    @Override
    public void register(Plugin plugin) {
        if(listener == null){
            listener = new SignGUIWrapper1_17();
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
                        BlockPosition position = new BlockPosition(block.getX(), block.getY(), block.getZ());
                        PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(position);
                        toNMS(player).b.sendPacket(packet);
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
                if (msg instanceof PacketPlayInUpdateSign) {
                    PacketPlayInUpdateSign packet = (PacketPlayInUpdateSign) msg;
                    if (onSignClosedMap.containsKey(p.getUniqueId())) {
                        onSignClosedMap.get(p.getUniqueId()).onSignClosed(packet.c());
                        onSignClosedMap.remove(p.getUniqueId());
                    }
                    removeChannelPipe(p);
                }
                super.channelRead(ctx, msg);
            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> {
            ChannelPipeline pipeline = toNMS(p).b.a.k.pipeline();
            if(pipeline.get(p.getName() + "_signgui") != null)
                return;

            pipeline.addBefore("packet_handler", p.getName() + "_signgui", channelDuplexHandler);
        }, 10L);
    }

    private void removeChannelPipe(Player p){
        ChannelPipeline pipeline = toNMS(p).b.a.k.pipeline();
        if(pipeline.get(p.getName() + "_signgui") == null)
            return;
        pipeline.remove(p.getName() + "_signgui");
    }

    @Override
    public void setOnSignClosedEvent(Player player, SignGUI.OnSignClosed onSignClosed) {
        onSignClosedMap.put(player.getUniqueId(), onSignClosed);
    }

    private EntityPlayer toNMS(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

}
