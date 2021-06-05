package it.smallcode.smallpets.v1_12.listener.experience;
/*

Class created by SmallCode
25.08.2020 22:04

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.itemIDs.ItemIDs;
import it.smallcode.smallpets.core.manager.ExperienceManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockBreakListener implements Listener {

    private double xpMultiplier;

    public BlockBreakListener(double xpMultiplier){

        this.xpMultiplier = xpMultiplier;

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent e){

        Player p = e.getPlayer();

        if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard()){

            if(!WorldGuardImp.checkStateFlag(p, SmallFlags.GIVE_EXP))
                return;

        }

        ExperienceManager experienceManager = SmallPetsCommons.getSmallPetsCommons().getExperienceManager();

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        if(user != null){

            if(user.getSelected() != null){

                int id = e.getBlock().getTypeId();
                int subid = e.getBlock().getData();

                String type = ItemIDs.getTypeFromIDAndSubID(id, subid);

                if(type != null){

                    type = type.toLowerCase();

                    if(experienceManager.getExperienceTableAll().containsKey(type)){

                        double extraMultiplier = 1;
                        if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard())
                            extraMultiplier = WorldGuardImp.getDoubleFlagValue(p, SmallFlags.EXP_MODIFIER, 1D);

                        if(isFarmingBlock(e.getBlock().getType())){

                            int age = e.getBlock().getState().getData().getData();

                            if(e.getBlock().getType() != Material.BEETROOT_BLOCK){

                                if(age == 7){

                                    int exp = experienceManager.getExperienceTableAll().get(type);

                                    if (experienceManager.getPetTypeOfType(type) != user.getSelected().getPetType()) {

                                        exp /= 2;

                                    }

                                    user.getSelected().giveExp((int) (exp * xpMultiplier * extraMultiplier), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                                }

                            }else{

                                if(age == 3){

                                    int exp = experienceManager.getExperienceTableAll().get(type);

                                    if (experienceManager.getPetTypeOfType(type) != user.getSelected().getPetType()) {

                                        exp /= 2;

                                    }

                                    user.getSelected().giveExp((int) (exp * xpMultiplier * extraMultiplier), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

                                }

                            }

                        }else {

                            if(SmallPetsCommons.getSmallPetsCommons().getMetaDataUtils().getMetaData(e.getBlock(), "playerPlaced") != null)
                                return;

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

    private boolean isFarmingBlock(Material material){

        if(     material == Material.CROPS ||
                material == Material.POTATO ||
                material == Material.CARROT ||
                material == Material.BEETROOT_BLOCK){

            return true;

        }

        return false;

    }

}
