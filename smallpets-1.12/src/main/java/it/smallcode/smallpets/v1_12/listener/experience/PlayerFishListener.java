package it.smallcode.smallpets.v1_12.listener.experience;
/*

Class created by SmallCode
30.08.2020 11:49

*/

import it.smallcode.smallpets.core.itemIDs.ItemIDs;
import it.smallcode.smallpets.core.manager.ExperienceManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
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

                        String type = ItemIDs.getTypeFromIDAndSubID(item.getTypeId(), item.getData().getData()).toLowerCase();

                        if(experienceManager.getExperienceTableAll().containsKey(type)){

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
