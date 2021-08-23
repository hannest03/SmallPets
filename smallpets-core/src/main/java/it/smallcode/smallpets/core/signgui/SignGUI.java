package it.smallcode.smallpets.core.signgui;
/*

Class created by SmallCode
20.08.2021 20:15

*/

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SignGUI {

    private static VersionWrapper versionWrapper;

    private Plugin plugin;

    private Player player;

    private String[] lines;

    private OnSignClosed onSignClosed;

    public SignGUI(Plugin plugin, Player player, String[] lines, OnSignClosed onSignClosed) {
        this.plugin = plugin;
        this.player = player;
        this.lines = lines;
        this.onSignClosed = onSignClosed;

        versionWrapper.register(plugin);
    }

    public void open(){
        versionWrapper.setOnSignClosedEvent(player, onSignClosed);
        versionWrapper.openSign(plugin, player, lines);
    }

    public static class Builder{

        private Plugin plugin;

        private Player player = null;

        private String[] lines = new String[4];

        private OnSignClosed onSignClosed = null;

        public Builder(Plugin plugin){
            this.plugin = plugin;
        }

        public Builder setPlayer(Player player) {
            this.player = player;
            return this;
        }

        public Builder setLines(String[] lines){
            this.lines = lines;
            return this;
        }

        public Builder setOnSignClosed(OnSignClosed onSignClosed){
            this.onSignClosed = onSignClosed;
            return this;
        }

        public SignGUI create(){
            return new SignGUI(plugin, player, lines, onSignClosed);
        }

    }

    public interface OnSignClosed{
        void onSignClosed(String[] text);
    }

    public static void setVersionWrapper(VersionWrapper versionWrapper) {
        SignGUI.versionWrapper = versionWrapper;
    }
}
