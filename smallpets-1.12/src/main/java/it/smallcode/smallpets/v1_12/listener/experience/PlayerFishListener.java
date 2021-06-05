package it.smallcode.smallpets.v1_12.listener.experience;
/*

Class created by SmallCode
30.08.2020 11:49

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.itemIDs.ItemIDs;
import it.smallcode.smallpets.core.manager.ExperienceManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerFishListener implements Listener {

    private double xpMultiplier;

    public PlayerFishListener(double xpMultiplier){

        this.xpMultiplier = xpMultiplier;

    }

    @EventHandler
    public void onFish(PlayerFishEvent e){

        if(e.getState() == PlayerFishEvent.State.CAUGHT_FISH){

            if(e.getCaught() != null){

                if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard()){

                    if(!WorldGuardImp.checkStateFlag(e.getPlayer(), SmallFlags.GIVE_EXP))
                        return;

                }

                ExperienceManager experienceManager = SmallPetsCommons.getSmallPetsCommons().getExperienceManager();

                User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(e.getPlayer().getUniqueId().toString());

                if(user != null) {

                    if(user.getSelected() != null) {

                        Item itemEntity = (Item) e.getCaught();

                        ItemStack item = itemEntity.getItemStack();

                        String type = ItemIDs.getTypeFromIDAndSubID(item.getTypeId(), item.getData().getData()).toLowerCase();

                        if(experienceManager.getExperienceTableAll().containsKey(type)){

                            double extraMultiplier = 1;
                            if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard())
                                extraMultiplier = WorldGuardImp.getDoubleFlagValue(e.getPlayer(), SmallFlags.EXP_MODIFIER, 1D);

                            int exp = experienceManager.getExperienceTableAll().get(type);

                            if (experienceManager.getPetTypeOfType(type) != user.getSelected().getPetType()) {

                                exp /= 2;

                            }

                            user.getSelected().giveExp((int) (exp * xpMultiplier * extraMultiplier), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                        }

                    }

                }

            }

        }

    }

}
