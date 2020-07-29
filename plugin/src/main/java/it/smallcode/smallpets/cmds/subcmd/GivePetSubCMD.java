package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
15.07.2020 17:34

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class GivePetSubCMD extends SubCommand {

    public GivePetSubCMD(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void handleCommand(CommandSender s, String[] args) {

        if(args.length == 2) {

            if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {

                SmallPets.getInstance().getUserManager().giveUserPet(args[1].toLowerCase(), Bukkit.getPlayer(args[0]).getUniqueId().toString());

                s.sendMessage(SmallPets.getInstance().PREFIX + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("givePetSender")
                        .replaceAll("%pet_type%", SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("pet." + args[1]))
                        .replaceAll("%player%", args[0]));

                Bukkit.getPlayer(args[0]).sendMessage(SmallPets.getInstance().PREFIX + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("givePetReciever")
                        .replaceAll("%pet_type%", SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("pet." + args[1]))
                        .replaceAll("%sender%", s.getName()));

            } else {

                s.sendMessage(SmallPets.getInstance().PREFIX + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("playerIsntOnline"));

            }

        }else{

            s.sendMessage(SmallPets.getInstance().PREFIX + "/smallpets admin " + getHelp());

        }

    }

    @Override
    public String getHelp() {
        return getName() + " <user> <type>";
    }
}
