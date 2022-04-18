package it.smallcode.smallpets.core.logger;
/*

Class created by SmallCode
23.03.2022 17:29

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.Bukkit;

public class BukkitLogger implements Logger{
    @Override
    public void println(String msg) {
        final String prefix = SmallPetsCommons.getSmallPetsCommons().getPrefix() != null ? SmallPetsCommons.getSmallPetsCommons().getPrefix() : "";
        Bukkit.getConsoleSender().sendMessage(prefix + msg);
    }

    @Override
    public void warn(String msg) {
        println("§cWARN§7: " + msg);
    }

    @Override
    public void error(String msg) {
        println("§4ERROR§7: " + msg);
    }
}
