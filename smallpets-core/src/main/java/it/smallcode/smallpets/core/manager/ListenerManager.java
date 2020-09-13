package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
09.07.2020 17:47

*/

import org.bukkit.plugin.java.JavaPlugin;

public abstract class ListenerManager {

    private JavaPlugin plugin;

    public ListenerManager(JavaPlugin plugin){

        this.plugin = plugin;

    }

    public abstract void registerListener();

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
