package it.smallcode.smallpets.core.pets.logic;
/*

Class created by SmallCode
23.06.2021 22:18

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.animations.HoverAnimation;
import it.smallcode.smallpets.core.pets.animations.LevelOnehundredAnimation;
import it.smallcode.smallpets.core.pets.animations.WalkAwayFromPlayerAnimation;
import it.smallcode.smallpets.core.pets.animations.FollowPlayerAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class BasicLogic implements Logic{

    private Pet pet;
    private int taskID;

    private FollowPlayerAnimation followPlayerArmorStand;
    private HoverAnimation hoverAnimation;
    private LevelOnehundredAnimation levelOnehundredAnimation;
    private WalkAwayFromPlayerAnimation walkAwayFromPlayerAnimation;

    @Override
    public void start(Pet pet) {
        this.pet = pet;

        followPlayerArmorStand = new FollowPlayerAnimation(pet, 0.5);
        walkAwayFromPlayerAnimation = new WalkAwayFromPlayerAnimation(pet, 0.55);

        hoverAnimation = new HoverAnimation(pet, 0.025, 0.2, -0.5);

        if(pet.getLevel() == 100)
            levelOnehundredAnimation = new LevelOnehundredAnimation(pet);

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> {

            spawnParticles();

            Location locationPet = pet.getLocation();
            Location locationOwner = pet.getOwner().getLocation();

            double distance = Math.sqrt(Math.pow(locationPet.getX() - locationOwner.getX(), 2) + Math.pow(locationPet.getZ() - locationOwner.getZ(), 2));

            if (distance >= 2.5D || Math.abs(locationOwner.getY() - locationPet.getY()) > 1D)

                move();

            else
            if(distance <= 1.0D){

                moveAway();

            }else {

                idle();

            }

        }, 0, 0);

    }

    @Override
    public void update() {
        if(!pet.isActivated())
            return;

        if (pet.getLevel() == 100) {
            levelOnehundredAnimation = new LevelOnehundredAnimation(pet);
        }else {
            if (levelOnehundredAnimation != null) {
                levelOnehundredAnimation.cancel();
                levelOnehundredAnimation = null;
            }
        }
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public void moveAway(){

        walkAwayFromPlayerAnimation.move(pet.getOwner(), pet.getLocation());

    }

    public void move() {

        followPlayerArmorStand.move(pet.getOwner(), pet.getLocation());

    }

    public void idle() {

        hoverAnimation.hover(pet.getOwner(), pet.getLocation());

    }

    private void spawnParticles() {

        Location particleLoc = pet.getLocation().clone();

        particleLoc.setY(particleLoc.getY() + 0.7);

        for(Player p : getPlayersWithVisibleActivated(particleLoc)){

            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

            if(!SmallPetsCommons.getSmallPetsCommons().isUseProtocollib() || (user != null && user.getSettings().isShowPets())) {

                p.spawnParticle(pet.getParticle(), particleLoc, 0);

            }

        }

    }

    private List<Player> getPlayersWithVisibleActivated(Location location){

        List<Player> players = new LinkedList<>();

        for(Player all : Bukkit.getOnlinePlayers())
            if(all.getWorld().getName().equals(location.getWorld().getName())){

                User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(all.getUniqueId().toString());

                if(user != null && user.getSettings().isShowPets()) {

                    players.add(all);

                }

            }

        return players;

    }
}
