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
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.session.SessionManager;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.worldguard.handlers.ShowPetsHandler;
import org.bukkit.entity.Player;

public class WorldGuardImp {

    public WorldGuardImp(){

        SmallFlags.registerFlags();

    }

    public void registerSessionHandlers(){

        SessionManager sessionManager = WorldGuard.getInstance().getPlatform().getSessionManager();

        sessionManager.registerHandler(ShowPetsHandler.FACTORY, null);

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

}
