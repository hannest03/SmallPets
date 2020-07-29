package it.smallcode.smallpets.v1_13;
/*

Class created by SmallCode
10.07.2020 15:10

*/

import it.smallcode.smallpets.languages.Language;
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.InventoryCache;
import it.smallcode.smallpets.manager.ListenerManager;
import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.v1_13.listener.BlockPlaceListener;
import it.smallcode.smallpets.v1_15.listener.*;
import it.smallcode.smallpets.v1_13.listener.UnlockListener;
import it.smallcode.smallpets.v1_15.listener.abilities.PlayerMoveListener;
import it.smallcode.smallpets.v1_15.listener.abilities.EntityDamageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager1_13 extends ListenerManager {

    public ListenerManager1_13(JavaPlugin plugin) {
        super(plugin);
    }

    private UserManager userManager;
    private PetMapManager petMapManager;
    private InventoryCache inventoryCache;
    private String prefix;
    private double xpMultiplier;

    private boolean useProtocollib;

    private LanguageManager languageManager;

    public ListenerManager1_13(JavaPlugin plugin, UserManager userManager, PetMapManager petMapManager, LanguageManager languageManager, InventoryCache inventoryCache, String prefix, double xpMultiplier, boolean useProtocollib) {

        super(plugin);

        this.languageManager = languageManager;

        this.userManager = userManager;
        this.petMapManager = petMapManager;
        this.inventoryCache = inventoryCache;
        this.prefix = prefix;
        this.xpMultiplier = xpMultiplier;

        this.useProtocollib = useProtocollib;

    }

    @Override
    public void registerListener() {

        //ABILITIES

        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(userManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(userManager), getPlugin());

        //OTHERS

        Bukkit.getPluginManager().registerEvents(new PetLevelUpListener(languageManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(userManager, prefix, languageManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new UnlockListener(getPlugin(), languageManager, userManager, prefix), getPlugin());
        Bukkit.getPluginManager().registerEvents(new ArmorStandInteractListener(petMapManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(getPlugin()), getPlugin());
        Bukkit.getPluginManager().registerEvents(new GiveExpListener(getPlugin(), userManager, xpMultiplier), getPlugin());
        Bukkit.getPluginManager().registerEvents(new WorldChangeListener(userManager, getPlugin(), useProtocollib), getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(userManager, getPlugin()), getPlugin());

    }
}
