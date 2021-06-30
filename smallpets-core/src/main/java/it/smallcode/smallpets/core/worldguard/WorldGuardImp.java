package it.smallcode.smallpets.core.worldguard;
/*

Class created by SmallCode
05.02.2021 19:09

*/

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DoubleFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.session.SessionManager;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.worldguard.handlers.AllowAbilitiesHandler;
import it.smallcode.smallpets.core.worldguard.handlers.ShowPetsHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WorldGuardImp {

    public WorldGuardImp(){

        SmallFlags.registerFlags();

    }

    public void registerSessionHandlers(){
        try {
            SessionManager sessionManager = WorldGuard.getInstance().getPlatform().getSessionManager();
            sessionManager.registerHandler(ShowPetsHandler.FACTORY, null);
            sessionManager.registerHandler(AllowAbilitiesHandler.FACTORY, null);
        }catch (NullPointerException ex){
            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§4ERROR§8: §7" + ex.getMessage());
        }
    }

    public static boolean checkStateFlag(LocalPlayer localPlayer, Location location, StateFlag flag){

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        ApplicableRegionSet set = query.getApplicableRegions(location);

        if(set.testState(localPlayer, flag)){

            return true;

        }

        return false;

    }

    public static boolean checkStateFlag(Player p, StateFlag flag){

        if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard()){

            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);

            return checkStateFlag(localPlayer, BukkitAdapter.adapt(p.getLocation()), flag);

        }

        return false;

    }

    public static double getDoubleFlagValue(Player p, DoubleFlag flag, double defaultValue){

        if(!SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard())
            return defaultValue;

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);

        return getDoubleFlagValue(localPlayer, BukkitAdapter.adapt(p.getLocation()), flag, defaultValue);

    }

    public static double getDoubleFlagValue(LocalPlayer localPlayer, Location location, DoubleFlag flag, double defaultValue){

        if(!SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard())
            return defaultValue;

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        ApplicableRegionSet set = query.getApplicableRegions(location);

        Double value = set.queryValue(localPlayer, flag);

        if(value == null)
            return defaultValue;

        return value;



    }

}
