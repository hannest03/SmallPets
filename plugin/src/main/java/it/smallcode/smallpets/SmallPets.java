package it.smallcode.smallpets;
/*

Class created by SmallCode
02.07.2020 13:34

*/

import it.smallcode.smallpets.cmds.PetCMD;
import it.smallcode.smallpets.pets.PetManager;
import it.smallcode.smallpets.pets.PetMapManager;
import it.smallcode.smallpets.pets.v1_15.PetMapManager1_15;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SmallPets extends JavaPlugin {

    private static SmallPets instance;

    private PetMapManager petMapManager;
    private PetManager petManager;

    public final String PREFIX = "§e○§6◯  SmallPets §e◆ ";

    @Override
    public void onEnable() {

        instance = this;

        if(!selectPetMapManager())
            return;

        petManager = new PetManager();

        Bukkit.getPluginCommand("pet").setExecutor(new PetCMD());

        Bukkit.getConsoleSender().sendMessage(PREFIX + "Plugin initialized");

    }

    @Override
    public void onDisable() {

        petManager.despawnAll();

    }

    private boolean selectPetMapManager(){

        String version = Bukkit.getServer().getClass().getPackage().getName();

        version = version.substring(version.lastIndexOf('.'));

        version = version.replace(".v", "");

        if(version.startsWith("1_8")){

            petMapManager = new PetMapManager1_15();

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

}
