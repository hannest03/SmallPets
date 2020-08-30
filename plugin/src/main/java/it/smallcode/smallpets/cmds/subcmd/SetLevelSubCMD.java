package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
14.08.2020 14:28

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SmallPetsCMD;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.manager.types.User;
import it.smallcode.smallpets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class SetLevelSubCMD extends SubCommand {

    public SetLevelSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.ADMIN);

        help += " <user> <type> <level>";

    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(args.length == 3) {

            if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {

                User user = SmallPets.getInstance().getUserManager().getUser(Bukkit.getPlayer(args[0]).getUniqueId().toString());

                if(user != null){

                    if(user.getPetFromType(args[1]) != null){

                        if(isInteger(args[2])){

                            Pet pet = user.getPetFromType(args[1]);

                            int level = Integer.valueOf(args[2]);

                            if(level > 100)
                                level = 100;

                            if(level <= 0)
                                level = 1;

                            long exp = pet.getExpForLevel(level) +1;

                            exp -= pet.getXp();

                            pet.giveExp((int) exp, SmallPets.getInstance());

                        }else{

                            s.sendMessage(SmallPets.getInstance().PREFIX + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("notInteger"));

                        }

                    }else{

                        s.sendMessage(SmallPets.getInstance().PREFIX + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("petNotUnlocked"));

                    }

                }else{

                    s.sendMessage(SmallPets.getInstance().PREFIX + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("userDataNotFound"));

                }

            } else {

                s.sendMessage(SmallPets.getInstance().PREFIX + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("playerIsntOnline"));

            }

        }else{

            s.sendMessage(SmallPets.getInstance().PREFIX + "/smallpets admin " + getHelp());

        }

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
