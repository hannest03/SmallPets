package it.smallcode.smallpets;
/*

Class created by SmallCode
02.07.2020 13:34

*/

import it.smallcode.smallpets.cmds.SmallPetsCMD;
import it.smallcode.smallpets.listener.*;
import it.smallcode.smallpets.manager.*;
import it.smallcode.smallpets.metrics.Metrics;
import it.smallcode.smallpets.pets.v1_15.InventoryManager1_15;
import it.smallcode.smallpets.pets.v1_15.PetMapManager1_15;
import it.smallcode.smallpets.pets.v1_16.InventoryManager1_16;
import it.smallcode.smallpets.pets.v1_16.PetMapManager1_16;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SmallPets extends JavaPlugin {

    private static SmallPets instance;

    private PetMapManager petMapManager;

    private UserManager userManager;

    private InventoryManager inventoryManager;
    private InventoryCache inventoryCache;

    public final String PREFIX = "§e○§6◯  SmallPets §e◆ ";

    private double xpMultiplier;

    @Override
    public void onEnable() {

        instance = this;

        this.loadConfig();

        inventoryCache = new InventoryCache();

        if(!selectRightVersion())
            return;

        userManager = new UserManager();

        //Registering all listeners

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorStandInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new PetLevelUpListener(), this);
        Bukkit.getPluginManager().registerEvents(new GiveExpListener(), this);
        Bukkit.getPluginManager().registerEvents(new UnlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);

        //Registering all commands

        Bukkit.getPluginCommand("smallpets").setExecutor(new SmallPetsCMD());

        //Registering bStats

        Metrics metrics = new Metrics(this, 8071);

        //Loading the users which are online

        for(Player all : Bukkit.getOnlinePlayers()){

            userManager.loadUser(all.getUniqueId().toString(), petMapManager);

        }

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Registering crafting recipes...");

        petMapManager.registerCraftingRecipe(this);

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Registered crafting recipes!");

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Plugin initialized");

    }

    @Override
    public void onDisable() {

        userManager.despawnPets();
        userManager.saveUsers();

        inventoryCache.removeAll();

    }

    public void loadConfig(){

        FileConfiguration cfg = this.getConfig();

        cfg.addDefault("xpMultiplier", 1D);

        getConfig().options().copyDefaults(true);

        saveConfig();
        reloadConfig();

        this.xpMultiplier = cfg.getDouble("xpMultiplier");

    }

    /**
     *
     * Selects the right version for the server
     *
     * @return a boolean if the right version was found
     */
    private boolean selectRightVersion(){

        String version = Bukkit.getServer().getClass().getPackage().getName();

        version = version.substring(version.lastIndexOf('.'));

        version = version.replace(".v", "");

        if(version.startsWith("1_15") || version.startsWith("1_14")){

            petMapManager = new PetMapManager1_15();
            inventoryManager = new InventoryManager1_15(inventoryCache);

        }else if(version.startsWith("1_16")){

            petMapManager = new PetMapManager1_16();
            inventoryManager = new InventoryManager1_16(inventoryCache);

        }else{

            Bukkit.getConsoleSender().sendMessage(PREFIX + "Not supported version");

            Bukkit.getPluginManager().disablePlugin(this);

            return false;

        }

        return true;

    }

    public static SmallPets getInstance() {
        return instance;
    }

    public PetMapManager getPetMapManager() {
        return petMapManager;
    }

    public InventoryCache getInventoryCache() {
        return inventoryCache;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public double getXpMultiplier() {
        return xpMultiplier;
    }
}
