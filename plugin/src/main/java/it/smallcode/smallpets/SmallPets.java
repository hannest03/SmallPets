package it.smallcode.smallpets;
/*

Class created by SmallCode
02.07.2020 13:34

*/

import it.smallcode.smallpets.cmds.SmallPetsCMD;
import it.smallcode.smallpets.listener.InventoryClickListener;
import it.smallcode.smallpets.listener.QuitListener;
import it.smallcode.smallpets.manager.InventoryCache;
import it.smallcode.smallpets.manager.InventoryManager;
import it.smallcode.smallpets.manager.PetManager;
import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.pets.v1_15.InventoryManager1_15;
import it.smallcode.smallpets.pets.v1_15.PetMapManager1_15;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SmallPets extends JavaPlugin {

    private static SmallPets instance;

    private PetMapManager petMapManager;
    private PetManager petManager;

    private InventoryManager inventoryManager;
    private InventoryCache inventoryCache;

    public final String PREFIX = "§e○§6◯  SmallPets §e◆ ";

    @Override
    public void onEnable() {

        instance = this;

        inventoryCache = new InventoryCache();

        if(!selectRightVersion())
            return;

        petManager = new PetManager();

        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);

        Bukkit.getPluginCommand("smallpets").setExecutor(new SmallPetsCMD());

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Plugin initialized");

    }

    @Override
    public void onDisable() {

        petManager.despawnAll();

        inventoryCache.removeAll();

    }

    private boolean selectRightVersion(){

        String version = Bukkit.getServer().getClass().getPackage().getName();

        version = version.substring(version.lastIndexOf('.'));

        version = version.replace(".v", "");

        if(version.startsWith("1_15")){

            petMapManager = new PetMapManager1_15();
            inventoryManager = new InventoryManager1_15(inventoryCache);

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

    public PetManager getPetManager(){

        return petManager;

    }

    public InventoryCache getInventoryCache() {
        return inventoryCache;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
