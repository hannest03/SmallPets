package it.smallcode.smallpets.core.worldguard.handlers;
/*

Class created by SmallCode
16.05.2021 20:20

*/

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;

import java.util.Set;

public class AllowAbilitiesHandler extends Handler {

    public static final Factory FACTORY = new Factory() {

        @Override
        public AllowAbilitiesHandler create(Session session) {

            return new AllowAbilitiesHandler(session);

        }

    };

    public AllowAbilitiesHandler(Session session) {

        super(session);

    }

    @Override
    public boolean onCrossBoundary(LocalPlayer player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {

        boolean oldAllowAbilities = WorldGuardImp.checkStateFlag(player, from, SmallFlags.ALLOW_ABILITIES);
        boolean newAllowAbilities = toSet.testState(player, SmallFlags.ALLOW_ABILITIES);

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(player.getUniqueId().toString());

        if(user == null)
            return true;

        if(user.getSelected() == null)
            return true;

        for(Ability ability : user.getSelected().getAbilities()){

            if(!(ability instanceof StatBoostAbility))
                continue;

            StatBoostAbility statBoostAbility = (StatBoostAbility) ability;

            if(!oldAllowAbilities && newAllowAbilities)
                statBoostAbility.addBoost(user.getSelected().getOwner(), ability);

            if(oldAllowAbilities && !newAllowAbilities)
                statBoostAbility.removeBoost(user.getSelected().getOwner(), ability);

        }

        return true;

    }
}
