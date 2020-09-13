package it.smallcode.smallpets.v1_13;
/*

Class created by SmallCode
10.07.2020 15:10

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.*;
import it.smallcode.smallpets.v1_13.listener.BlockPlaceListener;
import it.smallcode.smallpets.v1_13.listener.InventoryClickListener;
import it.smallcode.smallpets.v1_15.listener.*;
import it.smallcode.smallpets.v1_13.listener.UnlockListener;
import it.smallcode.smallpets.v1_15.listener.abilities.PlayerMoveListener;
import it.smallcode.smallpets.v1_15.listener.abilities.EntityDamageListener;
import it.smallcode.smallpets.v1_15.listener.experience.BlockBreakListener;
import it.smallcode.smallpets.v1_15.listener.experience.EntityDeathLListener;
import it.smallcode.smallpets.v1_15.listener.experience.FurnaceSmeltListener;
import it.smallcode.smallpets.v1_15.listener.experience.PlayerFishListener;
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

    private InventoryManager inventoryManager;
    private LanguageManager languageManager;
    private ExperienceManager experienceManager;

    public ListenerManager1_13(JavaPlugin plugin, UserManager userManager, PetMapManager petMapManager, LanguageManager languageManager, InventoryCache inventoryCache, String prefix, double xpMultiplier, boolean useProtocollib, InventoryManager inventoryManager, ExperienceManager experienceManager) {

        super(plugin);

        this.languageManager = languageManager;
        this.inventoryManager = inventoryManager;
        this.experienceManager = experienceManager;

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

        //EXPERIENCE

        //--- Combat

        Bukkit.getPluginManager().registerEvents(new EntityDeathLListener(getPlugin(), userManager, experienceManager, xpMultiplier), getPlugin());

        //--- Mining && Foraging && Farming

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(getPlugin(), userManager, experienceManager, xpMultiplier), getPlugin());
        Bukkit.getPluginManager().registerEvents(new FurnaceSmeltListener(getPlugin(), userManager, experienceManager, xpMultiplier), getPlugin());

        //--- Fishing

        Bukkit.getPluginManager().registerEvents(new PlayerFishListener(getPlugin(), userManager, experienceManager, xpMultiplier), getPlugin());

        //OTHERS

        Bukkit.getPluginManager().registerEvents(new PetLevelUpListener(languageManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(userManager, prefix, languageManager, getPlugin(), inventoryManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(inventoryManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new UnlockListener(getPlugin(), languageManager, userManager, prefix), getPlugin());
        Bukkit.getPluginManager().registerEvents(new ArmorStandInteractListener(petMapManager, languageManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(getPlugin()), getPlugin());
        Bukkit.getPluginManager().registerEvents(new WorldChangeListener(userManager, getPlugin(), useProtocollib), getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(userManager, getPlugin()), getPlugin());

    }
}
