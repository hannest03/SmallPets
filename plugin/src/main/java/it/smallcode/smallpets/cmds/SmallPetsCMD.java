package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
02.07.2020 15:25

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.manager.types.User;
import it.smallcode.smallpets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SmallPetsCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if (s instanceof Player) {

            Player p = (Player) s;

            if (args.length == 0) {

                User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

                if (user != null) {

                    List<Pet> pets = user.getPets();

                    SmallPets.getInstance().getInventoryManager().openPetsMenu(pets, p);

                    p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);

                } else {

                    p.sendMessage(SmallPets.getInstance().PREFIX + "Your user data couldn't be found!");

                }

                return false;

            } else if (args.length == 4) {

                if (args[0].equalsIgnoreCase("admin")) {

                    if (p.hasPermission("smallpets.admin")) {

                        if (args[1].equalsIgnoreCase("givepet")) {

                            if (p.hasPermission("smallpets.admin.givepet") || p.hasPermission("smallpets.admin.*")) {

                                if (Bukkit.getPlayer(args[2]) != null && Bukkit.getPlayer(args[2]).isOnline()) {

                                    SmallPets.getInstance().getUserManager().giveUserPet(args[3], Bukkit.getPlayer(args[2]).getUniqueId().toString());

                                    p.sendMessage(SmallPets.getInstance().PREFIX + "Gave the " + args[3] + " pet to " + args[2] + "!");

                                    return false;

                                } else {

                                    p.sendMessage(SmallPets.getInstance().PREFIX + "The player isn't online!");

                                    return false;

                                }

                            } else {

                                p.sendMessage(SmallPets.getInstance().PREFIX + "You haven't got the permission to do that!");

                                return false;

                            }

                        }

                    } else {

                        p.sendMessage(SmallPets.getInstance().PREFIX + "You haven't got the permission to do that!");

                    }

                }

            }

            s.sendMessage(SmallPets.getInstance().PREFIX + "/smallpets");
            s.sendMessage(SmallPets.getInstance().PREFIX + "/smallpets admin givepet <user> <type>");

        } else {

            s.sendMessage(SmallPets.getInstance().PREFIX + "This command is only for players");

        }

        return false;
    }
}
