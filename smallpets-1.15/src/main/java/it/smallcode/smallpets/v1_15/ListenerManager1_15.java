package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
09.07.2020 17:48

*/

import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.manager.*;
import it.smallcode.smallpets.v1_15.listener.*;
import it.smallcode.smallpets.v1_15.listener.abilities.EntityDamageListener;
import it.smallcode.smallpets.v1_15.listener.abilities.PlayerMoveListener;
import it.smallcode.smallpets.v1_15.listener.experience.BlockBreakListener;
import it.smallcode.smallpets.v1_15.listener.experience.EntityDeathLListener;
import it.smallcode.smallpets.v1_15.listener.experience.FurnaceSmeltListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager1_15 extends ListenerManager {

    private UserManager userManager;
    private ExperienceManager experienceManager;
    private PetMapManager petMapManager;
    private InventoryCache inventoryCache;
    private String prefix;
    private double xpMultiplier;
    private boolean useProtocollib;

    private InventoryManager inventoryManager;
    private LanguageManager languageManager;

    public ListenerManager1_15(JavaPlugin plugin, UserManager userManager, PetMapManager petMapManager, LanguageManager languageManager, InventoryCache inventoryCache, String prefix, double xpMultiplier, boolean useProtocollib, InventoryManager inventoryManager, ExperienceManager experienceManager) {

        super(plugin);

        this.experienceManager = experienceManager;
        this.languageManager = languageManager;
        this.inventoryManager = inventoryManager;

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

        //--- Mining

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(getPlugin(), userManager, experienceManager, xpMultiplier), getPlugin());
        Bukkit.getPluginManager().registerEvents(new FurnaceSmeltListener(getPlugin(), userManager, experienceManager, xpMultiplier), getPlugin());

        //OTHERS

        Bukkit.getPluginManager().registerEvents(new ArmorStandInteractListener(petMapManager, languageManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(getPlugin()), getPlugin());
        //Bukkit.getPluginManager().registerEvents(new GiveExpListener(getPlugin(), userManager, xpMultiplier), getPlugin());
        Bukkit.getPluginManager().registerEvents(new PetLevelUpListener(languageManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new UnlockListener(getPlugin(), languageManager, userManager, prefix), getPlugin());
        Bukkit.getPluginManager().registerEvents(new WorldChangeListener(userManager, getPlugin(), useProtocollib), getPlugin());
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(userManager, getPlugin()), getPlugin());
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(userManager, prefix, languageManager, getPlugin(), inventoryManager), getPlugin());
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(inventoryManager), getPlugin());

    }
}
