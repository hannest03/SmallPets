package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
26.10.2020 20:49

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.abilities.templates.InBiomeAbility;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SpeedBoostInBiomeAbility extends InBiomeAbility {

    private double speed;

    public SpeedBoostInBiomeAbility(){

        super(new LinkedList<>());

        speed = 0;

    }

    public SpeedBoostInBiomeAbility(List<Biome> biome, double speed) {

        super(biome);

        this.speed = speed;

    }

    @AbilityEventHandler
    public void onEnterBiome(EnterBiomeEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getUser().getSelected().getAbility(getID());

            if(speedBoostInBiomeAbility.containsBiome(e.getBiome())){

                e.getPlayer().setWalkSpeed((float) (e.getPlayer().getWalkSpeed() + speedBoostInBiomeAbility.getSpeed()));

                debug("enterBiome setspeed " + e.getPlayer().getWalkSpeed() );

            }

        }

    }

    @AbilityEventHandler
    public void onExitBiome(ExitBiomeEvent e){

        if(e.getUser().getSelected().hasAbility(getID())) {

            SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getUser().getSelected().getAbility(getID());

            if(speedBoostInBiomeAbility.containsBiome(e.getPrevBiome())){

                e.getPlayer().setWalkSpeed((float) (e.getPlayer().getWalkSpeed() - speedBoostInBiomeAbility.getSpeed()));

                debug("exitBiome setspeed " + e.getPlayer().getWalkSpeed() );

            }

        }

    }

    @AbilityEventHandler
    public void onServerShutdown(ServerShutdownEvent e){

        if(e.getUser() != null && e.getUser().getSelected() != null)
        if(e.getUser().getSelected().hasAbility(getID())){

            Player p = Bukkit.getPlayer(UUID.fromString(e.getUser().getUuid()));

            if(p != null && p.isOnline()){

                SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getUser().getSelected().getAbility(getID());

                if(speedBoostInBiomeAbility.containsBiome(p.getLocation().getBlock().getBiome())){

                    p.setWalkSpeed((float) (p.getWalkSpeed() - speedBoostInBiomeAbility.getSpeed()));

                    debug("shutdown setspeed " + p.getWalkSpeed() );

                }

            }

        }

    }

    @AbilityEventHandler
    public void onQuit(QuitEvent e){

        if(e.getUser().getSelected().hasAbility(getID())){

            Player p = Bukkit.getPlayer(UUID.fromString(e.getUser().getUuid()));

            if(p != null && p.isOnline()){

                SpeedBoostInBiomeAbility speedBoostInBiomeAbility = (SpeedBoostInBiomeAbility) e.getUser().getSelected().getAbility(getID());

                if(speedBoostInBiomeAbility.containsBiome(p.getLocation().getBlock().getBiome())){

                    p.setWalkSpeed((float) (p.getWalkSpeed() - speedBoostInBiomeAbility.getSpeed()));

                    debug("quit setspeed " + p.getWalkSpeed() );

                }

            }

        }

    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){

        if(e.getPet().hasAbility(getID())) {

            SpeedBoostInBiomeAbility ability = (SpeedBoostInBiomeAbility) e.getPet().getAbility(getID());

            Player p = e.getOwner();

            if(ability.containsBiome(p.getLocation().getBlock().getBiome())){

                p.setWalkSpeed((float) (p.getWalkSpeed() - ability.getSpeed()));

                debug("deselect setspeed " + p.getWalkSpeed());

            }

        }

    }

    public double getSpeed() {
        return speed;
    }
}
