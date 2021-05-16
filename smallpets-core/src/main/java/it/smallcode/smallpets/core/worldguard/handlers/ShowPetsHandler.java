package it.smallcode.smallpets.core.worldguard.handlers;
/*

Class created by SmallCode
22.02.2021 20:55

*/

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;

import java.util.Set;

public class ShowPetsHandler extends Handler {

    public static final Factory FACTORY = new Factory() {

        @Override
        public ShowPetsHandler create(Session session) {
            return new ShowPetsHandler(session);
        }

    };

    public ShowPetsHandler(Session session) {

        super(session);

    }

    @Override
    public boolean onCrossBoundary(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {

        boolean oldShowPets = WorldGuardImp.checkStateFlag(player, from, SmallFlags.SHOW_PETS);
        boolean newShowPets = toSet.testState(player, SmallFlags.SHOW_PETS);

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(player.getUniqueId().toString());

        if(user == null)
            return true;

        if(oldShowPets && newShowPets)
            return true;

        if(!oldShowPets && !newShowPets) {

            if(user.getSelected() != null)
                if(user.getSelected().isActivated())
                    user.despawnSelected();

            return true;

        }

        // -> Show Pets

        if(newShowPets){

            user.spawnSelected();

        }else{

            user.despawnSelected();

        }

        return true;

    }
}
