package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
02.07.2020 15:25

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.subcmd.*;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SmallPetsCMD implements CommandExecutor {

    private ArrayList<SubCommand> subCommands;

    public SmallPetsCMD(){

        subCommands = new ArrayList<>();

        subCommands.add(new PetTypesSubCMD("pettypes", "smallpets.pettypes"));
        subCommands.add(new GivePetSubCMD("givepet", "smallpets.givepet"));
        subCommands.add(new RemovePetSubCMD("removePet", "smallpets.removepet"));
        subCommands.add(new GiveExperienceSubCMD("giveexp", "smallpets.giveexp"));
        subCommands.add(new SetLevelSubCMD("setlevel", "smallpets.setlevel"));
        subCommands.add(new ReloadSubCMD("reload", "smallpets.reload"));

        subCommands.add(new DiscordSubCMD("discord", ""));
        subCommands.add(new DonateSubCMD("donate", ""));

    }

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if (args.length == 0) {

            if (s instanceof Player) {

                Player p = (Player) s;

                if(p.hasPermission("smallpets.useInventory")) {

                    User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

                    if (user != null) {

                        List<Pet> pets = user.getPets();

                        SmallPets.getInstance().getInventoryManager().openPetsMenu(pets, p);

                        p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);

                    } else {

                        p.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("userDataNotFound"));

                    }

                }else{

                    s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("noPerms"));

                }

            } else {

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("commandIsOnlyForPlayers"));

            }

            return false;

        }

        //smallpets admin test
        //smallpets discord

        Optional<SubCommand> optionalSubCommand = subCommands.stream().filter(subCommand -> {

            if(args.length >= subCommand.getSubCommandType().getMinArgs() +1){

                String typeName = "";

                if(subCommand.getSubCommandType().getMinArgs() > 0){

                    typeName = args[subCommand.getSubCommandType().getMinArgs() -1];

                }

                if(subCommand.getSubCommandType().getName().equalsIgnoreCase(typeName)){

                    return subCommand.getName().equalsIgnoreCase(args[subCommand.getSubCommandType().getMinArgs()]);

                }

            }

            return false;

        }).findFirst();

        if(optionalSubCommand.isPresent()){

            SubCommand subCommand = optionalSubCommand.get();

            String[] passArgs = new String[args.length - (subCommand.getSubCommandType().getMinArgs() +1)];

            for (int i = 0; i < passArgs.length; i++) {

                passArgs[i] = args[i + (subCommand.getSubCommandType().getMinArgs() +1)];

            }

            subCommand.command(s, passArgs);

            return false;

        }

        sendHelp(s);

        return false;

    }

    private void sendHelp(CommandSender s){

        s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets");

        for(SubCommand subCommand : subCommands){

            s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets " + subCommand.getSubCommandType().getName() + " " + subCommand.getHelp());

        }

    }

}
