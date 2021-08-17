package it.smallcode.smallpets.core.abilities.abilities;
/*

Class created by SmallCode
07.08.2021 20:28

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.*;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.utils.CircleUtils;
import it.smallcode.smallpets.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.*;

public class FlameCircleAbility extends Ability {

    private int taskId = -1;

    private Location location = null;
    private Particle particle = Particle.FLAME;

    private double radius = 2D;
    private double spaceBetween = 0.5D;

    @AbilityEventHandler
    public void onMove(MoveEvent e){
        if(e.getUser().getSelected().hasAbility(getID())){
            location = e.getTo().clone();
            location.setY(location.getY() + 0.25D);
        }
    }

    @AbilityEventHandler
    public void onSelect(PetSelectEvent e){
        if(e.getPet().hasAbility(getID())) {
            addBoost(e.getOwner(), e.getPet().getAbility(getID()));
        }
    }

    @AbilityEventHandler
    public void onDeselect(PetDeselectEvent e){
        if(e.getPet().hasAbility(getID())) {
            removeBoost(e.getOwner(), e.getPet().getAbility(getID()));
        }
    }

    @AbilityEventHandler
    public void onQuit(QuitEvent e){
        if(e.getUser().getSelected().hasAbility(getID())) {
            removeBoost(e.getUser().getSelected().getOwner(), e.getUser().getSelected().getAbility(getID()));
        }
    }

    @AbilityEventHandler
    public void onJoin(JoinEvent e){
        if(e.getUser().getSelected().hasAbility(getID())) {
            addBoost(e.getUser().getSelected().getOwner(), e.getUser().getSelected().getAbility(getID()));
        }
    }

    @AbilityEventHandler
    public void onShutdown(ServerShutdownEvent e){
        if(e.getUser() != null && e.getUser().getSelected() != null)
            if(e.getUser().getSelected().hasAbility(getID())) {
                removeBoost(e.getUser().getSelected().getOwner(), e.getUser().getSelected().getAbility(getID()));
            }
    }

    @Override
    public void addBoost(Player p, Ability ability) {
        if(taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
        }

        location = p.getLocation();
        location.setY(location.getY() + 0.25D);

        taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), new Runnable() {
            @Override
            public void run() {
                if(location != null){
                    CircleUtils.drawCircle(location, radius, spaceBetween, particle);
                }
            }
        }, 0, 20);

    }

    @Override
    public void removeBoost(Player p, Ability ability) {
        if(taskId != -1){
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
    }

    @Override
    public List<String> getAbilityTooltip(Pet pet) {
        List<String> lore = new ArrayList<>();
        if(getAbilityType() == AbilityType.ABILITY)
            lore.add("§6" + getName());
        String description = getDescription();
        description = StringUtils.addLineBreaks(description, 30);
        for(String s : description.split("\n"))
            lore.add("§7" + s);
        return lore;
    }
}
