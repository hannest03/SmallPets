package it.smallcode.smallpets.core.pets.interact;
/*

Class created by SmallCode
20.09.2021 14:28

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetInteractHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.Optional;

public class InteractSetupBukkit implements PetInteractHandler.Setup, Listener {
    @Override
    public void setup() {
        Bukkit.getPluginManager().registerEvents(this, SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());
    }
    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        int entityId = e.getRightClicked().getEntityId();
        Player p = e.getPlayer();
        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());
        if(user != null){
            Optional<Pet> optPet = user.getPets().stream().filter(pet -> pet.isEntity(entityId)).findFirst();
            if(optPet.isPresent()){
                PetInteractHandler.interactPet(p, optPet.get());
            }
        }
    }
}
