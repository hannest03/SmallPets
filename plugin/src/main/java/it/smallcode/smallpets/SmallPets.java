package it.smallcode.smallpets;
/*

Class created by SmallCode
02.07.2020 13:34

*/

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SmallPets extends JavaPlugin {

    private static SmallPets instance;

    @Override
    public void onEnable() {

        instance = this;

        Bukkit.getConsoleSender().sendMessage("§4Moin");

    }

    public static SmallPets getInstance() {
        return instance;
    }
}
