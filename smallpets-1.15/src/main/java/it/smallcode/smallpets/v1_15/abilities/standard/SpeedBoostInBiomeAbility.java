package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
26.10.2020 20:49

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.InBiomeStatBoostAbility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpeedBoostInBiomeAbility extends InBiomeStatBoostAbility {

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
}
