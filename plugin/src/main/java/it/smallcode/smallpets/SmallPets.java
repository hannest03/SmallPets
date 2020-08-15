package it.smallcode.smallpets;
/*

Class created by SmallCode
02.07.2020 13:34

*/

import it.smallcode.smallpets.cmds.SmallPetsCMD;
import it.smallcode.smallpets.languages.LanguageManager;
import it.smallcode.smallpets.listener.JoinListener;
import it.smallcode.smallpets.listener.QuitListener;
import it.smallcode.smallpets.listener.WorldSaveListener;
import it.smallcode.smallpets.manager.*;
import it.smallcode.smallpets.metrics.Metrics;
import it.smallcode.smallpets.placeholderapi.SmallPetsExpansion;
import it.smallcode.smallpets.v1_12.InventoryManager1_12;
import it.smallcode.smallpets.v1_12.ListenerManager1_12;
import it.smallcode.smallpets.v1_12.PetMapManager1_12;
import it.smallcode.smallpets.v1_13.InventoryManager1_13;
import it.smallcode.smallpets.v1_13.ListenerManager1_13;
import it.smallcode.smallpets.v1_13.PetMapManager1_13;
import it.smallcode.smallpets.v1_15.InventoryManager1_15;
import it.smallcode.smallpets.v1_15.ListenerManager1_15;
import it.smallcode.smallpets.v1_15.PetMapManager1_15;
import it.smallcode.smallpets.v1_16.InventoryManager1_16;
import it.smallcode.smallpets.v1_16.ListenerManager1_16;
import it.smallcode.smallpets.v1_16.PetMapManager1_16;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class SmallPets extends JavaPlugin {

    private static SmallPets instance;

    private PetMapManager petMapManager;

    private UserManager userManager;

    private InventoryManager inventoryManager;
    private InventoryCache inventoryCache;

    private ListenerManager listenerManager;
    private LanguageManager languageManager;

    private ExperienceManager experienceManager;

    public final String PREFIX = "§e○§6◯  SmallPets §e◆ ";

    private double xpMultiplier;
    private boolean registerCraftingRecipes;

    public static boolean useProtocolLib = false;

    @Override
    public void onEnable() {

        instance = this;

        this.initConfig();
        this.loadConfig();

        inventoryCache = new InventoryCache();

        if(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null && Bukkit.getPluginManager().getPlugin("ProtocolLib").isEnabled()){

            useProtocolLib = true;

            Bukkit.getConsoleSender().sendMessage(PREFIX + "Found ProtocolLib, now using it.");

        }

        languageManager = new LanguageManager(this, PREFIX, this.getConfig().getString("language"));

        if(!selectRightVersion())
            return;

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Registering pets...");

        petMapManager.registerPets();

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Registered pets");

        if(registerCraftingRecipes) {

            Bukkit.getConsoleSender().sendMessage(PREFIX + "Registering crafting recipes...");

            petMapManager.registerCraftingRecipe(this, languageManager);

            Bukkit.getConsoleSender().sendMessage(PREFIX + "Registered crafting recipes!");

        }

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Loading experience table...");

        this.experienceManager = new ExperienceManager(this);

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Experience table loaded!");

        //Registering all listeners

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Registering listeners...");

        listenerManager.registerListener();

        Bukkit.getPluginManager().registerEvents(new JoinListener(userManager, petMapManager), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(userManager, inventoryCache), this);
        Bukkit.getPluginManager().registerEvents(new WorldSaveListener(), this);

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Registered listeners!");

        //Registering all commands

        Bukkit.getPluginCommand("smallpets").setExecutor(new SmallPetsCMD());

        //Registering PlaceholderAPI

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){

            Bukkit.getConsoleSender().sendMessage(PREFIX + "Registering PlaceholderAPI expansion...");

            new SmallPetsExpansion().register();

            Bukkit.getConsoleSender().sendMessage(PREFIX + "Registered PlaceholderAPI expansion!");

        }

        //Registering bStats

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Starting metrics...");

        Metrics metrics = new Metrics(this, 8071);

        metrics.addCustomChart(new Metrics.DrilldownPie("protocollib", () ->{

            Map<String, Map<String, Integer>> map = new HashMap<>();

            String version = Bukkit.getVersion();

            Map<String, Integer> entry = new HashMap<>();

            entry.put(version, 1);

            if(useProtocolLib){

                map.put("Uses protocollib", entry);

            }else{

                map.put("Doesn't use protocollib", entry);

            }

            return map;

        }));

        metrics.addCustomChart(new Metrics.SimplePie("languages", () -> languageManager.getLanguage().getLanguageName()));

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Metrics started!");

        //Loading the users which are online

        for(Player all : Bukkit.getOnlinePlayers()){

            userManager.loadUser(all.getUniqueId().toString(), petMapManager);

        }

        Bukkit.getPluginManager().registerEvents(new WorldSaveListener(), this);

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Plugin initialized");

    }

    @Override
    public void onDisable() {

        userManager.despawnPets();
        userManager.saveUsers();

        inventoryCache.removeAll();

    }

    public void initConfig(){

        FileConfiguration cfg = this.getConfig();

        cfg.addDefault("xpMultiplier", 1D);
        cfg.addDefault("language", "en");
        cfg.addDefault("registerCraftingRecipes", true);

        getConfig().options().copyDefaults(true);

        saveConfig();
        reloadConfig();

    }

    public void loadConfig(){

        reloadConfig();

        FileConfiguration cfg = this.getConfig();

        this.xpMultiplier = cfg.getDouble("xpMultiplier");
        this.registerCraftingRecipes = cfg.getBoolean("registerCraftingRecipes");

        if(inventoryManager != null){

            inventoryManager.setXpMultiplier(xpMultiplier);

        }

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

        if(version.startsWith("1_12")) {

            petMapManager = new PetMapManager1_12();
            inventoryManager = new InventoryManager1_12(inventoryCache, languageManager, xpMultiplier);
            userManager = new UserManager(this, languageManager, petMapManager, useProtocolLib);
            listenerManager = new ListenerManager1_12(this, getUserManager(), getPetMapManager(), languageManager, getInventoryCache(), PREFIX, xpMultiplier, useProtocolLib);

        }else if(version.startsWith("1_13")){

            petMapManager = new PetMapManager1_13();
            inventoryManager = new InventoryManager1_13(inventoryCache, languageManager, xpMultiplier);
            userManager = new UserManager(this, languageManager, petMapManager, useProtocolLib);
            listenerManager = new ListenerManager1_13(this, getUserManager(), getPetMapManager(), languageManager, getInventoryCache(), PREFIX, xpMultiplier, useProtocolLib);

        }else if(version.startsWith("1_15") || version.startsWith("1_14")){

            petMapManager = new PetMapManager1_15();
            inventoryManager = new InventoryManager1_15(inventoryCache, languageManager, xpMultiplier);
            userManager = new UserManager(this, languageManager, petMapManager, useProtocolLib);
            listenerManager = new ListenerManager1_15(this, getUserManager(), getPetMapManager(), languageManager, getInventoryCache(), PREFIX, xpMultiplier, useProtocolLib);

        }else if(version.startsWith("1_16")){

            petMapManager = new PetMapManager1_16();
            inventoryManager = new InventoryManager1_16(inventoryCache, languageManager, xpMultiplier);
            userManager = new UserManager(this, languageManager, petMapManager, useProtocolLib);
            listenerManager = new ListenerManager1_16(this, getUserManager(), getPetMapManager(), languageManager, getInventoryCache(), PREFIX, xpMultiplier, useProtocolLib);

        }else{

            Bukkit.getConsoleSender().sendMessage(PREFIX + "Not supported version");

            Bukkit.getPluginManager().disablePlugin(this);

            return false;

        }

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Loaded version " + version + "!");

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

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public double getXpMultiplier() {
        return xpMultiplier;
    }

    public ExperienceManager getExperienceManager() {
        return experienceManager;
    }
}
