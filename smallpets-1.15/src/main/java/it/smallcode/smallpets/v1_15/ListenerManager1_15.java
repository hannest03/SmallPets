package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
09.07.2020 17:48

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.*;
import it.smallcode.smallpets.v1_15.listener.*;
import it.smallcode.smallpets.v1_15.listener.experience.BlockBreakListener;
import it.smallcode.smallpets.v1_15.listener.experience.EntityDeathListener;
import it.smallcode.smallpets.v1_15.listener.experience.FurnaceSmeltListener;
import it.smallcode.smallpets.v1_15.listener.experience.PlayerFishListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager1_15 extends ListenerManager {

    private double xpMultiplier;
    private boolean useProtocollib;

    public ListenerManager1_15(double xpMultiplier, boolean useProtocollib) {

        this.xpMultiplier = xpMultiplier;

        this.useProtocollib = useProtocollib;

    }

    @Override
    public void registerListener() {

        JavaPlugin plugin = SmallPetsCommons.getSmallPetsCommons().getJavaPlugin();

        //EXPERIENCE

        //--- Combat

        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(xpMultiplier), plugin);

        //--- Mining && Foraging && Farming

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(xpMultiplier), plugin);
        Bukkit.getPluginManager().registerEvents(new FurnaceSmeltListener(xpMultiplier), plugin);

        //--- Fishing

        Bukkit.getPluginManager().registerEvents(new PlayerFishListener(xpMultiplier), plugin);

        //OTHERS

        Bukkit.getPluginManager().registerEvents(new ArmorStandInteractListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new PetLevelUpListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new WorldChangeListener(useProtocollib), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), plugin);

    }
}
