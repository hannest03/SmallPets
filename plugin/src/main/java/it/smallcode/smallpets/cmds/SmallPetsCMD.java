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

        if (args.length == 0) {

            if (s instanceof Player) {

                Player p = (Player) s;

                User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

                if (user != null) {

                    List<Pet> pets = user.getPets();

                    SmallPets.getInstance().getInventoryManager().openPetsMenu(pets, p);

                    p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);

                } else {

                    p.sendMessage(SmallPets.getInstance().PREFIX + "Your user data couldn't be found!");

                }

            } else {

                s.sendMessage(SmallPets.getInstance().PREFIX + "This command is only for players");

            }

            return false;

        } else if (args.length == 4) {

            if (args[0].equalsIgnoreCase("admin")) {

                if (s.hasPermission("smallpets.admin")) {

                    if (args[1].equalsIgnoreCase("givepet")) {

                        if (s.hasPermission("smallpets.admin.givepet") || s.hasPermission("smallpets.admin.*")) {

                            if (Bukkit.getPlayer(args[2]) != null && Bukkit.getPlayer(args[2]).isOnline()) {

                                SmallPets.getInstance().getUserManager().giveUserPet(args[3], Bukkit.getPlayer(args[2]).getUniqueId().toString());

                                s.sendMessage(SmallPets.getInstance().PREFIX + "Gave the " + args[3] + " pet to " + args[2] + "!");

                                Bukkit.getPlayer(args[2]).sendMessage(SmallPets.getInstance().PREFIX + "You received the " + args[3] + " pet from " + s.getName() + "!");

                                return false;

                            } else {

                                s.sendMessage(SmallPets.getInstance().PREFIX + "The player isn't online!");

                                return false;

                            }

                        } else {

                            s.sendMessage(SmallPets.getInstance().PREFIX + "You haven't got the permission to do that!");

                            return false;

                        }

                    }

                } else {

                    s.sendMessage(SmallPets.getInstance().PREFIX + "You haven't got the permission to do that!");

                    return false;

                }

            }

            s.sendMessage(SmallPets.getInstance().PREFIX + "/smallpets");
            s.sendMessage(SmallPets.getInstance().PREFIX + "/smallpets admin givepet <user> <type>");

        }

        return false;

    }
}
