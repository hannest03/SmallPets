package it.smallcode.smallpets.v1_15.listener.experience;
/*

Class created by SmallCode
30.08.2020 11:49

*/

import it.smallcode.smallpets.manager.ExperienceManager;
import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerFishListener implements Listener {

    private JavaPlugin plugin;

    private UserManager userManager;
    private ExperienceManager experienceManager;

    private double xpMultiplier;

    public PlayerFishListener(JavaPlugin plugin, UserManager userManager, ExperienceManager experienceManager, double xpMultiplier){

        this.userManager = userManager;
        this.experienceManager = experienceManager;

        this.xpMultiplier = xpMultiplier;

    }

    @EventHandler
    public void onFish(PlayerFishEvent e){

        if(e.getState() == PlayerFishEvent.State.CAUGHT_FISH){

            if(e.getCaught() != null){

                User user = userManager.getUser(e.getPlayer().getUniqueId().toString());

                if(user != null) {

                    if(user.getSelected() != null) {

                        Item itemEntity = (Item) e.getCaught();

                        ItemStack item = itemEntity.getItemStack();

                        String type = item.getType().toString().toLowerCase();

                        System.out.println(type);

                        if(experienceManager.getExperienceTableAll().containsKey(type)){

                            System.out.println("Exp");

                            int exp = experienceManager.getExperienceTableAll().get(type);

                            if (experienceManager.getPetTypeOfType(type) != user.getSelected().getPetType()) {

                                exp /= 2;

                            }

                            user.getSelected().giveExp((int) (exp * xpMultiplier), plugin);

                        }

                    }

                }

            }

        }

    }

}
