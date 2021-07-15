package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
26.10.2020 20:49

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.InBiomeAbility;
import it.smallcode.smallpets.core.abilities.templates.InBiomeStatBoostAbility;
import it.smallcode.smallpets.core.abilities.templates.StatBoostAbility;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SpeedBoostInBiomeAbility extends InBiomeStatBoostAbility {

    public SpeedBoostInBiomeAbility() {
        this(new LinkedList<>(), 0);
    }

    public SpeedBoostInBiomeAbility(List<Biome> biomes, double minSpeed, double maxSpeed) {
        super(biomes, maxSpeed, minSpeed, NumberDisplayType.TWO_DECIMAL_PLACES);
    }

    public SpeedBoostInBiomeAbility(List<Biome> biomes, double speed) {
        this(biomes, 0, speed);
    }

    @AbilityEventHandler
    public void onEnterBiome(EnterBiomeEvent e) {
        if (e.getUser().getSelected().hasAbility(getID())) {
            SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getUser().getSelected().getAbility(getID());
            if (speedBoostInBiomeAbility.containsBiome(e.getBiome())) {
                addBoost(e.getPlayer(), speedBoostInBiomeAbility);
            }
        }
    }

    @AbilityEventHandler
    public void onExitBiome(ExitBiomeEvent e) {
        if (e.getUser().getSelected().hasAbility(getID())) {
            SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getUser().getSelected().getAbility(getID());
            if (speedBoostInBiomeAbility.containsBiome(e.getPrevBiome())) {
                removeBoost(e.getPlayer(), speedBoostInBiomeAbility);
            }
        }
    }

    @AbilityEventHandler
    public void onServerShutdown(ServerShutdownEvent e) {
        if (e.getUser() != null && e.getUser().getSelected() != null)
            if (e.getUser().getSelected().hasAbility(getID())) {
                Player p = Bukkit.getPlayer(UUID.fromString(e.getUser().getUuid()));
                if (p != null && p.isOnline()) {
                    SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getUser().getSelected().getAbility(getID());
                    if (speedBoostInBiomeAbility.containsBiome(p.getLocation().getBlock().getBiome())) {
                        removeBoost(p, speedBoostInBiomeAbility);
                    }
                }
            }
    }

    @AbilityEventHandler
    public void onQuit(QuitEvent e) {
        if (e.getUser().getSelected().hasAbility(getID())) {
            Player p = Bukkit.getPlayer(UUID.fromString(e.getUser().getUuid()));
            if (p != null && p.isOnline()) {
                SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getUser().getSelected().getAbility(getID());
                if (speedBoostInBiomeAbility.containsBiome(p.getLocation().getBlock().getBiome())) {
                    removeBoost(p, speedBoostInBiomeAbility);
                }
            }
        }
    }

    @AbilityEventHandler
    public void onLevelUp(PetLevelUpEvent e) {
        if (e.getPet().hasAbility(getID())){
            SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getPet().getAbility(getID());
            if(!speedBoostInBiomeAbility.containsBiome(e.getPet().getOwner().getLocation().getBlock().getBiome()))
                return;
            removeBoost(e.getPet().getOwner(), speedBoostInBiomeAbility);
            addBoost(e.getPet().getOwner(), speedBoostInBiomeAbility);
        }
    }

    @AbilityEventHandler
    public void onSelect(PetSelectEvent e){
        if(e.getPet().hasAbility(getID())){
            SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getPet().getAbility(getID());
            if(!speedBoostInBiomeAbility.containsBiome(e.getPet().getOwner().getLocation().getBlock().getBiome()))
                return;
            addBoost(e.getOwner(), e.getPet().getAbility(getID()));
        }
    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){
        if(e.getPet().hasAbility(getID())) {
            SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getPet().getAbility(getID());

            Player p = e.getOwner();
            if(speedBoostInBiomeAbility.containsBiome(p.getLocation().getBlock().getBiome())){
                removeBoost(p, speedBoostInBiomeAbility);
            }
        }
    }

    @Override
    public void addBoost(Player p, Ability ability) {
        StatBoostAbility statBoostAbility = (StatBoostAbility) ability;

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());
        if(user == null)
            return;

        double boost = statBoostAbility.getExtraStat(user.getSelected().getLevel());

        SmallPetsCommons.getSmallPetsCommons().getSpeedModifierUtils().addModifier(p, ability.getID(), boost, AttributeModifier.Operation.ADD_NUMBER);
        debug(getID() + " add modifier " + boost);
    }

    @Override
    public void removeBoost(Player p, Ability ability) {
        SmallPetsCommons.getSmallPetsCommons().getSpeedModifierUtils().removeModifier(p, ability.getID());
        debug(getID() + " remove modifier");
    }
}
