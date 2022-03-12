package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
02.07.2020 15:25

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.subcmd.*;
import it.smallcode.smallpets.cmds.subcmd.debug.TestMetaDataSubCMD;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SmallPetsCMD implements CommandExecutor, TabCompleter {

    private ArrayList<SubCommand> subCommands;

    public SmallPetsCMD(){

        subCommands = new ArrayList<>();

        subCommands.add(new PetTypesSubCMD("pettypes", "smallpets.pettypes"));
        subCommands.add(new GivePetSubCMD("givepet", "smallpets.givepet"));
        subCommands.add(new GiveUnlockItemSubCMD("giveunlockitem", "smallpets.giveunlockitem"));
        subCommands.add(new RemovePetSubCMD("removePet", "smallpets.removepet"));
        subCommands.add(new GiveExperienceSubCMD("giveexp", "smallpets.giveexp"));
        subCommands.add(new SetLevelSubCMD("setlevel", "smallpets.setlevel"));
        subCommands.add(new ReloadSubCMD("reload", "smallpets.reload"));
        subCommands.add(new SaveLanguagesCMD("savelanguages", "smallpets.savelanguages"));
        subCommands.add(new ConvertSubCMD("convert", "smallpets.convert"));

        subCommands.add(new TestMetaDataSubCMD("testmetadata", "smallpets.debug.testmetadata"));

        subCommands.add(new DiscordSubCMD("discord", null));
        subCommands.add(new DonateSubCMD("donate", null));
        subCommands.add(new SelectSubCMD("select", null));
        subCommands.add(new CreditsSubCMD("credits", null));

    }

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if (args.length == 0) {

            if (s instanceof Player) {

                Player p = (Player) s;
                if(SmallPetsCommons.getSmallPetsCommons().getDisabledWorlds().contains(p.getLocation().getWorld().getName())) {
                    p.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("pets_disabled_world"));
                    return false;
                }

                if(!p.hasPermission("smallpets.dontUseInventory") || p.isOp()) {

                    User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

                    if (user != null) {

                        SmallPets.getInstance().getInventoryManager().openPetsMenu(0, p);

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

        Optional<SubCommand> optionalSubCommand = subCommands.stream().filter(subCommand -> {

            if(!subCommand.getSubCommandType().isActive())
                return false;

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
        s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets select <type>");

        for(SubCommand subCommand : subCommands){

            s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets " + subCommand.getSubCommandType().getName() + " " + subCommand.getHelp());

        }

    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command c, String label, String[] args) {

        List<String> options = SubCommandType.handleAutoComplete(s, args);

        subCommands.stream()
                .filter(subCommand -> subCommand.getSubCommandType().getMinArgs() < args.length)
                .filter(subCommand -> subCommand.hasPermission(s))
                .forEach(subCommand ->  {

                    if(!subCommand.getSubCommandType().isActive())
                        return;

                    String[] name = subCommand.getSubCommandType().getName().split(" ");

                    //Remove wrong commands with wrong subcommand type
                    for(int i = 0; i < subCommand.getSubCommandType().getMinArgs(); i++){

                        if(args.length >= i && name.length >= i)
                            if(!args[i].equalsIgnoreCase(name[i]))
                                return;

                    }

                    //Remove wrong subcommands
                    if(args.length > (subCommand.getSubCommandType().getMinArgs() +1))
                        if (!args[subCommand.getSubCommandType().getMinArgs()].equalsIgnoreCase(subCommand.getName()))
                            return;

                    String[] passArgs = new String[0];

                    if(args.length - (subCommand.getSubCommandType().getMinArgs()+1) > 0)
                        passArgs = new String[args.length - (subCommand.getSubCommandType().getMinArgs() +1)];

                    if(passArgs.length > 0)
                        for(int i = 0; i < passArgs.length; i++){

                            passArgs[i] = args[i + (subCommand.getSubCommandType().getMinArgs()+1)];

                        }

                    List<String> optionsSubCommand = subCommand.handleAutoComplete(s, passArgs);

                    if(optionsSubCommand == null)
                        return;

                    //Check if contains in string already given
                    optionsSubCommand.removeIf(string -> !string.contains(args[args.length-1]) && !string.isEmpty());

                    //No doubled options
                    options.removeAll(optionsSubCommand);
                    options.addAll(optionsSubCommand);

                }
        );


        return options;

    }
}
