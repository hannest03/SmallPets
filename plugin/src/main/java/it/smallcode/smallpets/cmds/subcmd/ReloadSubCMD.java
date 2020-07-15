package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
15.07.2020 22:04

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadSubCMD extends SubCommand {


    public ReloadSubCMD(String name, String permission) {
        super(name, permission);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(args[0].equalsIgnoreCase("all")){

            SmallPets.getInstance().loadConfig();
            SmallPets.getInstance().getLanguageManager().loadLanguage();

        }else if(args[0].equalsIgnoreCase("config")){

            SmallPets.getInstance().loadConfig();

        }else if(args[0].equalsIgnoreCase("language")){

            SmallPets.getInstance().getLanguageManager().loadLanguage();

        }else{

            s.sendMessage(SmallPets.getInstance().PREFIX + "/smallpets admin " + getHelp());

        }

    }

    @Override
    public String getHelp() {
        return getName() + "<all/config/language>";
    }
}
