package it.smallcode.smallpets.core.pets.interact;
/*

Class created by SmallCode
14.03.2022 17:07

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.function.Consumer;

public class BukkitInteractHandler implements PetInteractHandler.Handler, Listener {
    private Consumer<PetInteractHandler.InteractEvent> consumer;

    @Override
    public void setup(Consumer<PetInteractHandler.InteractEvent> consumer) {
        this.consumer = consumer;
        Bukkit.getPluginManager().registerEvents(this, SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        int entityId = e.getRightClicked().getEntityId();
        Player p = e.getPlayer();
        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());
        if(user != null && user.getSelected().isEntity(entityId)){
            e.setCancelled(true);
            consumer.accept(new PetInteractHandler.InteractEvent(p, user.getSelected()));
        }
    }
}
