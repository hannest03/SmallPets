package it.smallcode.smallpets.v1_15.listener.experience;
/*

Class created by SmallCode
25.08.2020 22:04

*/

import it.smallcode.smallpets.manager.ExperienceManager;
import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
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

                String type = e.getBlock().getType().toString().toLowerCase();

                if(experienceManager.getExperienceTableAll().containsKey(type)){

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
