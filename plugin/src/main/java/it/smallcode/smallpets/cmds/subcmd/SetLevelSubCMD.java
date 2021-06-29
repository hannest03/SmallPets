package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
14.08.2020 14:28

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SetLevelSubCMD extends SubCommand {

    public SetLevelSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.ADMIN);

        help += " <user> <uuid> <level>";

    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(args.length == 3) {

            if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {

                User user = SmallPets.getInstance().getUserManager().getUser(Bukkit.getPlayer(args[0]).getUniqueId().toString());

                if(user != null){
                    UUID petUUID = UUID.fromString(args[1]);
                    if(user.getPetFromUUID(petUUID) != null){

                        if(isInteger(args[2])){

                            Pet pet = user.getPetFromUUID(petUUID);

                            int level = Integer.valueOf(args[2]);

                            if(level > 100)
                                level = 100;

                            if(level <= 0)
                                level = 1;

                            long exp = pet.getExpForLevel(level) +1;

                            exp -= pet.getXp();

                            pet.giveExp((int) exp, SmallPets.getInstance());

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
    public List<String> handleAutoComplete(CommandSender s, String[] args) {

        List<String> options = super.handleAutoComplete(s, args);

        if(args.length == 1){

            options = new LinkedList<>();

            List<String> finalOptions = options;
            Bukkit.getOnlinePlayers().forEach(player -> finalOptions.add(player.getName()));

            options = finalOptions;

        }

        if(args.length == 2){

            options = new LinkedList<>();

            if(Bukkit.getOfflinePlayer(args[0]) != null && Bukkit.getOfflinePlayer(args[0]).isOnline()) {

                User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());

                if (user != null) {

                    List<String> finalOptions = options;
                    user.getPets().forEach(pet -> finalOptions.add(pet.getUuid().toString()));

                    options = finalOptions;

                }

            }

        }

        return options;

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
