package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
15.07.2020 22:04

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import org.bukkit.command.CommandSender;

public class ReloadSubCMD extends SubCommand {

    public ReloadSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.ADMIN);

        help += " <all/config/experienceTable/language>";

    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(args.length == 1) {

            if (args[0].equalsIgnoreCase("all")) {

                SmallPets.getInstance().loadConfig();
                SmallPets.getInstance().getLanguageManager().loadLanguage(SmallPets.getInstance().getConfig().getString("language"));
                SmallPets.getInstance().getExperienceManager().reload();

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("reloaded")
                        .replaceAll("%type%", "all"));

            } else if (args[0].equalsIgnoreCase("config")) {

                SmallPets.getInstance().loadConfig();

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("reloaded")
                        .replaceAll("%type%", "config"));

            } else if (args[0].equalsIgnoreCase("language")) {

                SmallPets.getInstance().reloadConfig();
                SmallPets.getInstance().getLanguageManager().loadLanguage(SmallPets.getInstance().getConfig().getString("language"));

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("reloaded")
                        .replaceAll("%type%", "language"));

            }else if(args[0].equalsIgnoreCase("experienceTable")){

                SmallPets.getInstance().getExperienceManager().reload();

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("reloaded")
                        .replaceAll("%type%", "experienceTable"));

            }else{

                s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets admin " + getHelp());

            }

        } else {

            s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets admin " + getHelp());

        }

    }

}
