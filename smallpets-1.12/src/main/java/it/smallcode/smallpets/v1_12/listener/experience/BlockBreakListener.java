package it.smallcode.smallpets.v1_12.listener.experience;
/*

Class created by SmallCode
25.08.2020 22:04

*/

import it.smallcode.smallpets.core.itemIDs.ItemIDs;
import it.smallcode.smallpets.core.manager.ExperienceManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockBreakListener implements Listener {

    private JavaPlugin plugin;

    private UserManager userManager;
    private ExperienceManager experienceManager;

    private double xpMultiplier;

    public BlockBreakListener(JavaPlugin plugin, UserManager userManager, ExperienceManager experienceManager, double xpMultiplier){

        this.userManager = userManager;
        this.experienceManager = experienceManager;

        this.xpMultiplier = xpMultiplier;

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Player p = e.getPlayer();

        User user = userManager.getUser(p.getUniqueId().toString());

        if(user != null){

            if(user.getSelected() != null){

                int id = e.getBlock().getTypeId();
                int subid = e.getBlock().getData();

                String type = ItemIDs.getTypeFromIDAndSubID(id, subid);

                if(type != null){

                    type = type.toLowerCase();

                    if(experienceManager.getExperienceTableAll().containsKey(type)){

                        if(isFarmingBlock(e.getBlock().getType())){

                            int age = e.getBlock().getState().getData().getData();

                            if(e.getBlock().getType() != Material.BEETROOT_BLOCK){

                                if(age == 7){

                                    int exp = experienceManager.getExperienceTableAll().get(type);

                                    if (experienceManager.getPetTypeOfType(type) != user.getSelected().getPetType()) {

                                        exp /= 2;

                                    }

                                    user.getSelected().giveExp((int) (exp * xpMultiplier), plugin);

                                }

                            }else{

                                if(age == 3){

                                    int exp = experienceManager.getExperienceTableAll().get(type);

                                    if (experienceManager.getPetTypeOfType(type) != user.getSelected().getPetType()) {

                                        exp /= 2;

                                    }

                                    user.getSelected().giveExp((int) (exp * xpMultiplier), plugin);

                                }

                            }

                        }else {

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
