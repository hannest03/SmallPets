package it.smallcode.smallpets;
/*

Class created by SmallCode
02.07.2020 13:34

*/

import it.smallcode.smallpets.cmds.SmallPetsCMD;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.ServerShutdownEvent;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.listener.*;
import it.smallcode.smallpets.core.manager.*;
import it.smallcode.smallpets.metrics.Metrics;
import it.smallcode.smallpets.placeholderapi.SmallPetsExpansion;
import it.smallcode.smallpets.v1_12.*;
import it.smallcode.smallpets.v1_13.*;
import it.smallcode.smallpets.v1_15.*;
import it.smallcode.smallpets.v1_16.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SmallPets extends JavaPlugin {

    public static final String DISCORD_LINK = "https://discordapp.com/invite/62wbxdg";
    public static final String DONATION_LINK = "https://ko-fi.com/smallcode";

    private static SmallPets instance;

    private double xpMultiplier;
    private boolean registerCraftingRecipes;

    public static boolean useProtocolLib = false;

    @Override
    public void onEnable() {

        instance = this;

        SmallPetsCommons.getSmallPetsCommons().setJavaPlugin(this);

        this.initConfig();
        if(!this.loadConfig()) return;

        SmallPetsCommons.getSmallPetsCommons().setInventoryCache(new InventoryCache());

        if(getConfig().getBoolean("useProtocolLib")){

            if(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null && Bukkit.getPluginManager().getPlugin("ProtocolLib").isEnabled()){

                useProtocolLib = true;

                Bukkit.getConsoleSender().sendMessage(getPrefix() + "Found ProtocolLib, now using it.");

            }

        }

        SmallPetsCommons.getSmallPetsCommons().setLanguageManager(new LanguageManager(this, getPrefix(), this.getConfig().getString("language")));

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Loading experience table...");

        SmallPetsCommons.getSmallPetsCommons().setExperienceManager(new ExperienceManager(this));

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Experience table loaded!");

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

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Registered listeners!");

        //Registering all commands

        Bukkit.getPluginCommand("smallpets").setExecutor(new SmallPetsCMD());

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

            if(useProtocolLib){

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

            getUserManager().loadUser(all.getUniqueId().toString(), getPetMapManager());

        }

        Bukkit.getPluginManager().registerEvents(new WorldSaveListener(), this);

        Bukkit.getConsoleSender().sendMessage("");

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Consider joining the discord server for news and test versions!");
        Bukkit.getConsoleSender().sendMessage(getPrefix() + "You can join with this link: " + DISCORD_LINK);

        Bukkit.getConsoleSender().sendMessage("");

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "One time donations are also appreciated: " + DONATION_LINK);

    }

    @Override
    public void onDisable() {

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

        getConfig().options().copyDefaults(true);

        saveConfig();
        reloadConfig();

    }

    public boolean loadConfig(){

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

        if(getInventoryManager() != null){

            getInventoryManager().setXpMultiplier(xpMultiplier);

        }

        return true;

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

            if(useProtocolLib)
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_12());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_12());
            SmallPetsCommons.getSmallPetsCommons().setNbtTagEditor(new INBTTagEditor1_12());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_12());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_12(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setUserManager(new UserManager( useProtocolLib));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_12(xpMultiplier, useProtocolLib));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_12());

        }else if(version.startsWith("1_13")){

            if(useProtocolLib)
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_13());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_13());
            SmallPetsCommons.getSmallPetsCommons().setNbtTagEditor(new INBTTagEditor1_13());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_13());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_13(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setUserManager(new UserManager(useProtocolLib));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_13(xpMultiplier, useProtocolLib));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_13());

        }else if(version.startsWith("1_15") || version.startsWith("1_14")){

            if(useProtocolLib)
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_15());
            SmallPetsCommons.getSmallPetsCommons().setNbtTagEditor(new INBTTagEditor1_15());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_15());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_15(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setUserManager(new UserManager( useProtocolLib));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_15(xpMultiplier, useProtocolLib));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_15());

        }else if(version.startsWith("1_16")){

            if(useProtocolLib)
                SmallPetsCommons.getSmallPetsCommons().setProtocolLibUtils(new ProtocolLibUtils1_16());

            SmallPetsCommons.getSmallPetsCommons().setSkullCreator(new SkullCreator1_16());
            SmallPetsCommons.getSmallPetsCommons().setNbtTagEditor(new INBTTagEditor1_16());
            SmallPetsCommons.getSmallPetsCommons().setMetaDataUtils(new MetaDataUtils1_15());

            SmallPetsCommons.getSmallPetsCommons().setPetMapManager(new PetMapManager1_16());
            SmallPetsCommons.getSmallPetsCommons().setInventoryManager(new InventoryManager1_16(xpMultiplier));
            SmallPetsCommons.getSmallPetsCommons().setUserManager(new UserManager( useProtocolLib));
            SmallPetsCommons.getSmallPetsCommons().setListenerManager(new ListenerManager1_16(xpMultiplier, useProtocolLib));

            SmallPetsCommons.getSmallPetsCommons().setAbilityManager(new AbilityManager1_16());

        }else{

            Bukkit.getConsoleSender().sendMessage(getPrefix() + "Not supported version");

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
