package it.smallcode.smallpets;
/*

Class created by SmallCode
02.07.2020 13:34

*/

import it.smallcode.smallpets.cmds.*;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.database.Database;
import it.smallcode.smallpets.core.database.SaveType;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.ServerShutdownEvent;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.listener.*;
import it.smallcode.smallpets.core.manager.*;
import it.smallcode.smallpets.metrics.*;
import it.smallcode.smallpets.placeholderapi.*;
import it.smallcode.smallpets.v1_12.*;
import it.smallcode.smallpets.v1_13.*;
import it.smallcode.smallpets.v1_15.*;
import it.smallcode.smallpets.v1_16.*;
import it.smallcode.smallpets.v1_17.*;
import it.smallcode.smallpets.v1_18.*;
import it.smallcode.smallpets.v1_19.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SmallPets extends JavaPlugin {

    public static final String DISCORD_LINK = "https://discordapp.com/invite/62wbxdg";
    public static final String DONATION_LINK = "https://ko-fi.com/smallcode";

    private static SmallPets instance;
    private static Database database;

    private double xpMultiplier;
    private boolean registerCraftingRecipes;

    private WorldGuardImp worldGuardImp;

    @Override
    public void onLoad() {

        instance = this;

        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("  §e ___  §6 ____");
        Bukkit.getConsoleSender().sendMessage("  §e|     §6|    |  §e" + getDescription().getName() + " §7v" + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("  §e|___  §6|____|  §8Made by " + getDescription().getAuthors().stream().collect(Collectors.joining(", ")));
        Bukkit.getConsoleSender().sendMessage("  §e    | §6|");
        Bukkit.getConsoleSender().sendMessage("  §e ___| §6|");
        Bukkit.getConsoleSender().sendMessage("");

        SmallPetsCommons.getSmallPetsCommons().setJavaPlugin(this);

        //SmallPetsCommons.getSmallPetsCommons().setAutoSaveManager(new AutoSaveManager());
        SmallPetsCommons.getSmallPetsCommons().setBackupManager(new BackupManager());

        this.initConfig();
        if(!this.loadConfig()) return;

        SmallPetsCommons.getSmallPetsCommons().setLanguageManager(new LanguageManager(this, getPrefix(), this.getConfig().getString("language")));

        SmallPetsCommons.getSmallPetsCommons().setInventoryCache(new InventoryCache(SmallPetsCommons.getSmallPetsCommons().getLanguageManager()));

        //Registering WorldGuard

        if(Bukkit.getPluginManager().getPlugin("WorldGuard") != null && !is1_12()){

            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Adding WorldGuard hook...");

            worldGuardImp = new WorldGuardImp();

            SmallPetsCommons.getSmallPetsCommons().setUseWorldGuard(true);

            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Added WorldGuard hook!");

        }

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Loading experience table...");

        SmallPetsCommons.getSmallPetsCommons().setExperienceManager(new ExperienceManager(this));

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Experience table loaded!");

    }

    @Override
    public void onEnable() {

        //SmallPetsCommons.getSmallPetsCommons().getAutoSaveManager().start();
        SmallPetsCommons.getSmallPetsCommons().getBackupManager().start();

        if(getConfig().getBoolean("useProtocolLib")){

            if(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null && Bukkit.getPluginManager().getPlugin("ProtocolLib").isEnabled()){

                SmallPetsCommons.getSmallPetsCommons().setUseProtocollib(true);

                Bukkit.getConsoleSender().sendMessage(getPrefix() + "Found ProtocolLib, now using it.");

            }

        }

        if(!selectRightVersion())
            return;

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registering abilities...");

        getAbilityManager().registerAbilities();

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registered abilities");

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registering pets...");

        getPetMapManager().registerPets();

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registered pets");

        if(registerCraftingRecipes) {

            Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registering crafting recipes...");

            getPetMapManager().registerCraftingRecipe();

            Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registered crafting recipes!");

        }

        //Registering all listeners

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registering listeners...");

        getListenerManager().registerListener();

        Bukkit.getPluginManager().registerEvents(new BlockInteractListener(), this);

        Bukkit.getPluginManager().registerEvents(new JoinListener(getUserManager(), getPetMapManager()), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(getUserManager(), getInventoryCache()), this);
        Bukkit.getPluginManager().registerEvents(new WorldSaveListener(), this);

        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new ShootProjectileListener(), this);

        Bukkit.getPluginManager().registerEvents(new UnlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new PetChangedWorldListener(), this);

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registered listeners!");

        //Registering all commands

        SmallPetsCMD smallPetsCMD = new SmallPetsCMD();

        getCommand("smallpets").setExecutor(smallPetsCMD);
        getCommand("smallpets").setTabCompleter(smallPetsCMD);

        //--------------------- Third Party Plugins

        //Registering PlaceholderAPI

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){

            Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registering PlaceholderAPI expansion...");

            new SmallPetsExpansion().register();

            Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registered PlaceholderAPI expansion!");

        }

        //Registering bStats

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Starting metrics...");

        Metrics metrics = new Metrics(this, 8071);

        metrics.addCustomChart(new Metrics.DrilldownPie("protocollib", () ->{

            Map<String, Map<String, Integer>> map = new HashMap<>();

            String version = Bukkit.getVersion();

            Map<String, Integer> entry = new HashMap<>();

            entry.put(version, 1);

            if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()){

                map.put("Uses protocollib", entry);

            }else{

                map.put("Doesn't use protocollib", entry);

            }

            return map;

        }));

        metrics.addCustomChart(new Metrics.SimplePie("languages", () -> getLanguageManager().getLanguage().getLanguageName()));

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Metrics started!");

        //Loading the users which are online

        for(Player all : Bukkit.getOnlinePlayers()){

            getUserManager().loadUser(all.getUniqueId().toString());

        }

        Bukkit.getPluginManager().registerEvents(new WorldSaveListener(), this);

        Bukkit.getConsoleSender().sendMessage("");

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Consider joining the discord server for news and test versions!");
        Bukkit.getConsoleSender().sendMessage(getPrefix() + "You can join with this link: " + DISCORD_LINK);

        Bukkit.getConsoleSender().sendMessage("");

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "One time donations are also appreciated: " + DONATION_LINK);

        Bukkit.getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                if(worldGuardImp != null) {
                    worldGuardImp.registerSessionHandlers();
                }
            }
        }, 1);

    }

    @Override
    public void onDisable() {

        /*
        if(SmallPetsCommons.getSmallPetsCommons().getAutoSaveManager() != null)
            SmallPetsCommons.getSmallPetsCommons().getAutoSaveManager().stop();
         */

        if(SmallPetsCommons.getSmallPetsCommons().getBackupManager() != null)
            SmallPetsCommons.getSmallPetsCommons().getBackupManager().stop();

        if(getUserManager() != null) {

            for (User user : getUserManager().getUsers()) {

                if (Bukkit.getOfflinePlayer(UUID.fromString(user.getUuid())).isOnline())
                    AbilityEventBus.post(new ServerShutdownEvent(user));

            }

            getUserManager().despawnPets();
            getUserManager().saveUsers();

        }

        if(getInventoryCache() != null)
            getInventoryCache().removeAll();

        database.disconnect();
    }

    public void initConfig(){

        FileConfiguration cfg = this.getConfig();

        cfg.addDefault("prefixPattern", "&e○&6◯  %plugin_name% &e◆ &7");
        cfg.addDefault("useProtocolLib", true);
        cfg.addDefault("xpMultiplier", 1D);
        cfg.addDefault("language", "en");
        cfg.addDefault("registerCraftingRecipes", true);
        cfg.addDefault("requirePermission", false);
        cfg.addDefault("xpToLevelTwo", 500);

        cfg.addDefault("showUnlockMessage", true);
        cfg.addDefault("activateParticles", true);
        cfg.addDefault("disabledWorlds", new LinkedList<>());

        // --- Auto Save

        //cfg.addDefault("autosave.enabled", true);
        //cfg.addDefault("autosave.interval", 15);

        // --- Backup

        cfg.addDefault("backup.enabled", true);
        cfg.addDefault("backup.interval", 60);
        cfg.addDefault("backup.backupLifetimeInMinutes", 43200);

        // --- Pet Lore

        List<String> petLore = new LinkedList<>();
        petLore.add("&8%pet_type%");
        petLore.add("");
        petLore.add("&7%abilities%");
        petLore.add("");
        petLore.add("%progress_bar%");

        cfg.addDefault("pet_lore", petLore);

        cfg.addDefault("saveType", SaveType.SQLITE.toString());

        cfg.addDefault("mysql.host", "localhost");
        cfg.addDefault("mysql.port", 3306);
        cfg.addDefault("mysql.database", "smallpets");
        cfg.addDefault("mysql.username", "root");
        cfg.addDefault("mysql.password", "");

        getConfig().options().copyDefaults(true);

        saveConfig();
        reloadConfig();

    }

    public boolean loadConfig(){
        /*
        if(SmallPetsCommons.getSmallPetsCommons().getAutoSaveManager() != null && this.isEnabled())
            SmallPetsCommons.getSmallPetsCommons().getAutoSaveManager().stop();
         */

        if(SmallPetsCommons.getSmallPetsCommons().getBackupManager() != null && this.isEnabled())
            SmallPetsCommons.getSmallPetsCommons().getBackupManager().stop();

        reloadConfig();

        FileConfiguration cfg = this.getConfig();

        Pet.setXpToLevelTwo(cfg.getLong("xpToLevelTwo"));

        String prefix = cfg.getString("prefixPattern");

        if(!prefix.contains("%plugin_name%")) {

            Bukkit.getConsoleSender().sendMessage( "§c" + getName() + ": deactivating, wrong prefix pattern! ");
            Bukkit.getConsoleSender().sendMessage( "§c" + getName() + ": PrefixPattern doesn't contain %plugin_name%: " + prefix);

            Bukkit.getPluginManager().disablePlugin(this);
            return false;

        }

        prefix = prefix.replaceAll("%plugin_name%", getName());
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);

        SmallPetsCommons.getSmallPetsCommons().setPrefix(prefix);

        this.xpMultiplier = cfg.getDouble("xpMultiplier");
        this.registerCraftingRecipes = cfg.getBoolean("registerCraftingRecipes");

        SmallPetsCommons.getSmallPetsCommons().setRequirePermission(cfg.getBoolean("requirePermission"));
        SmallPetsCommons.getSmallPetsCommons().setShowUnlockMessage(cfg.getBoolean("showUnlockMessage"));
        SmallPetsCommons.getSmallPetsCommons().setActivateParticles(cfg.getBoolean("activateParticles"));
        SmallPetsCommons.getSmallPetsCommons().setDisabledWorlds(cfg.getStringList("disabledWorlds"));

        if(getInventoryManager() != null){

            getInventoryManager().setXpMultiplier(xpMultiplier);

        }

        /*
        if(cfg.getBoolean("autosave.enabled")){

            SmallPetsCommons.getSmallPetsCommons().getAutoSaveManager().setInterval(cfg.getLong("autosave.interval"));

            if(this.isEnabled())
                SmallPetsCommons.getSmallPetsCommons().getAutoSaveManager().start();

        }
         */

        if(cfg.getBoolean("backup.enabled")){

            SmallPetsCommons.getSmallPetsCommons().getBackupManager().setInterval(cfg.getLong("backup.interval"));
            SmallPetsCommons.getSmallPetsCommons().getBackupManager().setBackupLifetimeInMinutes(cfg.getLong("backup.backupLifetimeInMinutes"));

            if(this.isEnabled())
                SmallPetsCommons.getSmallPetsCommons().getBackupManager().start();

        }

        if(cfg.getStringList("pet_lore") != null){
            List<String> petLore = cfg.getStringList("pet_lore");
            SmallPetsCommons.getSmallPetsCommons().setPetLore(petLore);
        }

        SaveType saveType;
        try {
            saveType = SaveType.valueOf(cfg.getString("saveType"));
        }catch (Exception ex){
            saveType = SaveType.SQLITE;
        }

        if(database != null){
            database.disconnect();
        }else {
            database = new Database();
        }
        switch (saveType){
            case SQLITE:{
                try {
                    database.connect(new File(getDataFolder(), "database.db").getPath());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Bukkit.getPluginManager().disablePlugin(this);
                }
                break;
            }
            case MYSQL:{
                Database.DatabaseConfig databaseConfig = new Database.DatabaseConfig();
                databaseConfig.host = cfg.getString("mysql.host");
                databaseConfig.port = cfg.getInt("mysql.port");
                databaseConfig.databaseName = cfg.getString("mysql.database");
                databaseConfig.username = cfg.getString("mysql.username");
                databaseConfig.password = cfg.getString("mysql.password");

                try {
                    database.connect(databaseConfig);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Bukkit.getPluginManager().disablePlugin(this);
                }
            }
        }

        return true;

    }

    private boolean is1_12(){

        String version = Bukkit.getServer().getClass().getPackage().getName();

        version = version.substring(version.lastIndexOf('.'));

        version = version.replace(".v", "");

        return version.startsWith("1_12");

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

        SmallPetsCommons.getSmallPetsCommons().setUserManager(new UserManager(database));

        if(version.startsWith("1_12")) {

            if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib())
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_12());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_12());
            SmallPetsCommons.getSmallPetsCommons().setINBTTagEditor(new INBTTagEditor1_12());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setHealthModifierUtils(new HealthModifierUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setSpeedModifierUtils(new SpeedModifierUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_12());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_12(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_12(xpMultiplier, SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_12());

        }else if(version.startsWith("1_13")){

            if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib())
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_13());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_13());
            SmallPetsCommons.getSmallPetsCommons().setINBTTagEditor(new INBTTagEditor1_13());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setHealthModifierUtils(new HealthModifierUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setSpeedModifierUtils(new SpeedModifierUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_13());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_13(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_13(xpMultiplier, SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_13());

        }else if(version.startsWith("1_15") || version.startsWith("1_14")){

            if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib())
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_15());
            SmallPetsCommons.getSmallPetsCommons().setINBTTagEditor(new INBTTagEditor1_15());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setHealthModifierUtils(new HealthModifierUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setSpeedModifierUtils(new SpeedModifierUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_15());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_15(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_15(xpMultiplier, SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_15());

        }else if(version.startsWith("1_16")){

            if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib())
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_16());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_16());
            SmallPetsCommons.getSmallPetsCommons().setINBTTagEditor(new INBTTagEditor1_16());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setHealthModifierUtils(new HealthModifierUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setSpeedModifierUtils(new SpeedModifierUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_16());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_16(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_16(xpMultiplier, SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_16());

        }else if(version.startsWith("1_17")){

            if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib())
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_17());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_17());
            SmallPetsCommons.getSmallPetsCommons().setINBTTagEditor(new INBTTagEditor1_17());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setHealthModifierUtils(new HealthModifierUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setSpeedModifierUtils(new SpeedModifierUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_17());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_17(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_17(xpMultiplier, SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_17());

        }else if(version.startsWith("1_18")){

            if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib())
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_18());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_18());
            SmallPetsCommons.getSmallPetsCommons().setINBTTagEditor(new INBTTagEditor1_18());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setHealthModifierUtils(new HealthModifierUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setSpeedModifierUtils(new SpeedModifierUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_18());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_18(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_18(xpMultiplier, SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_18());

        }else if(version.startsWith("1_19")){

            if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib())
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_19());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_19());
            SmallPetsCommons.getSmallPetsCommons().setINBTTagEditor(new INBTTagEditor1_19());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setHealthModifierUtils(new HealthModifierUtils1_15());
            SmallPetsCommons.getSmallPetsCommons().setSpeedModifierUtils(new SpeedModifierUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_19());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_19(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_19(xpMultiplier, SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_19());

        }else{

            Bukkit.getConsoleSender().sendMessage(getPrefix() + "§cNot supported version");

            Bukkit.getPluginManager().disablePlugin(this);

            return false;

        }

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Loaded version " + version + "!");

        return true;

    }

    public String getPrefix(){

        return SmallPetsCommons.getSmallPetsCommons().getPrefix();

    }

    public static SmallPets getInstance() {
        return instance;
    }

    public PetMapManager getPetMapManager() {
        return SmallPetsCommons.getSmallPetsCommons().getPetMapManager();
    }

    public InventoryCache getInventoryCache() {
        return SmallPetsCommons.getSmallPetsCommons().getInventoryCache();
    }

    public InventoryManager getInventoryManager() {
        return SmallPetsCommons.getSmallPetsCommons().getInventoryManager();
    }

    public UserManager getUserManager() {
        return SmallPetsCommons.getSmallPetsCommons().getUserManager();
    }

    public LanguageManager getLanguageManager() {
        return SmallPetsCommons.getSmallPetsCommons().getLanguageManager();
    }

    public double getXpMultiplier() {
        return xpMultiplier;
    }

    public ExperienceManager getExperienceManager() {
        return SmallPetsCommons.getSmallPetsCommons().getExperienceManager();
    }

    public AbilityManager getAbilityManager(){ return SmallPetsCommons.getSmallPetsCommons().getAbilityManager(); }

    public ListenerManager getListenerManager(){ return SmallPetsCommons.getSmallPetsCommons().getListenerManager(); }

}
