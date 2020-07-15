package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
02.07.2020 15:25

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.subcmd.GivePetSubCMD;
import it.smallcode.smallpets.cmds.subcmd.ReloadSubCMD;
import it.smallcode.smallpets.manager.types.User;
import it.smallcode.smallpets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SmallPetsCMD implements CommandExecutor {

    private ArrayList<SubCommand> subAdminCommands;

    public SmallPetsCMD(){

        subAdminCommands = new ArrayList<>();

        subAdminCommands.add(new GivePetSubCMD("givepet", "smallpets.givepet"));
        subAdminCommands.add(new ReloadSubCMD("reload", "smallpets.reload"));

    }

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

                    p.sendMessage(SmallPets.getInstance().PREFIX + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("userDataNotFound"));

                }

            } else {

                s.sendMessage(SmallPets.getInstance().PREFIX + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("commandIsOnlyForPlayers"));

            }

            return false;

        } else if (args.length >= 2) {

            if(args[0].equalsIgnoreCase("admin")){

                Optional<SubCommand> optSubCommand = subAdminCommands.stream().filter(subCommand -> subCommand.getName().equalsIgnoreCase(args[1])).findFirst();

                if(optSubCommand.isPresent()) {

                    String[] passArgs = new String[args.length - 2];

                    for (int i = 0; i < passArgs.length; i++) {

                        passArgs[i] = args[i + 2];

                    }

                    optSubCommand.get().command(s, passArgs);

                    return false;


                }

            }

        }

        sendHelp(s);

        return false;

    }

    private void sendHelp(CommandSender s){

        s.sendMessage(SmallPets.getInstance().PREFIX + "/smallpets");

        for(SubCommand subCommand : subAdminCommands){

            s.sendMessage(SmallPets.getInstance().PREFIX + "/smallpets admin " + subCommand.getHelp());

        }

    }

}
