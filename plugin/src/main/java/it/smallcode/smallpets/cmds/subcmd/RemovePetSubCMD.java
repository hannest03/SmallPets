package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
30.07.2020 09:16

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class RemovePetSubCMD extends SubCommand {

    public RemovePetSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.ADMIN);

        help += " <user> <uuid>";

    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(args.length == 2) {

            if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
                String uuid = Bukkit.getPlayer(args[0]).getUniqueId().toString();
                if(args[1].equalsIgnoreCase("*")){
                    SmallPets.getInstance().getUserManager().removeUserAllPets(uuid);
                    args[1] = "all";
                }else {
                    UUID petUUID = UUID.fromString(args[1]);
                    SmallPets.getInstance().getUserManager().removeUserPet(petUUID, uuid);
                }
                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("removePetSender")
                        .replaceAll("%pet_type%", args[1])
                        .replaceAll("%player%", args[0]));

                Bukkit.getPlayer(args[0]).sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("removePetReceiver")
                        .replaceAll("%pet_type%", args[1])
                        .replaceAll("%sender%", s.getName()));

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

                    options.add("*");

                    List<String> finalOptions = options;
                    user.getPets().forEach(pet -> finalOptions.add(pet.getUuid().toString()));

                    options = finalOptions;

                }

            }

        }

        return options;

    }

}
