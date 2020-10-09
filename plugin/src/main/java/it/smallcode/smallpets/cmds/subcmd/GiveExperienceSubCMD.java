package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
14.08.2020 14:01

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class GiveExperienceSubCMD extends SubCommand {


    public GiveExperienceSubCMD(String name, String permission) {
        super(name, permission);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(args.length == 3) {

            if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {

                User user = SmallPets.getInstance().getUserManager().getUser(Bukkit.getPlayer(args[0]).getUniqueId().toString());

                if(user != null){

                    if(user.getPetFromType(args[1]) != null){

                        if(isInteger(args[2])){

                            int exp = Integer.valueOf(args[2]);

                            user.getPetFromType(args[1]).giveExp(exp, SmallPets.getInstance());

                        }else{

                            s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("notInteger"));

                        }

                    }else{

                        s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("petNotUnlocked"));

                    }

                }else{

                    s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("userDataNotFound"));

                }

            } else {

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("playerIsntOnline"));

            }

        }else{

            s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets admin " + getHelp());

        }

    }

    @Override
    public String getHelp() {
        return getName() + " <user> <type> <exp>";
    }

    private boolean isInteger(String number){

        try{

            Integer.valueOf(number);

            return true;

        }catch (Exception ex){

            return false;

        }

    }

}
