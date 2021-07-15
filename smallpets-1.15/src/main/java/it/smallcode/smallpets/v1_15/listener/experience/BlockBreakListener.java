package it.smallcode.smallpets.v1_15.listener.experience;
/*

Class created by SmallCode
25.08.2020 22:04

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.ExperienceManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private double xpMultiplier;

    public BlockBreakListener(double xpMultiplier){

        this.xpMultiplier = xpMultiplier;

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent e){

        if(e.isCancelled())
            return;

        if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard()){

            if(!WorldGuardImp.checkStateFlag(e.getPlayer(), SmallFlags.GIVE_EXP))
                return;

        }

        Player p = e.getPlayer();

        ExperienceManager experienceManager = SmallPetsCommons.getSmallPetsCommons().getExperienceManager();

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

        if(user != null){

            if(user.getSelected() != null){

                String type = e.getBlock().getType().toString().toLowerCase();

                if(experienceManager.getExperienceTableAll().containsKey(type)){

                    double extraMultiplier = 1;

                    if(SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard())
                        extraMultiplier = WorldGuardImp.getDoubleFlagValue(p, SmallFlags.EXP_MODIFIER, 1D);

                    if(isFarmingBlock(e.getBlock().getType())){

                        String blockData = e.getBlock().getBlockData().getAsString();

                        int index = blockData.indexOf("age=");

                        index += 4;

                        blockData = blockData.substring(index, index + 1);

                        int age = Integer.parseInt(blockData);

                        if(e.getBlock().getType() != Material.BEETROOTS){

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

                        //CHECK IF BLOCK WAS PLACED BY PLAYER (RESETS WHEN SERVER RESTARTS)
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

    private boolean isFarmingBlock(Material material){

        if(     material == Material.WHEAT ||
                material == Material.POTATOES ||
                material == Material.CARROTS ||
                material == Material.BEETROOTS){

            return true;

        }

        return false;

    }

}
