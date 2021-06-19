package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
15.07.2020 17:34

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

public class GivePetSubCMD extends SubCommand {

    public GivePetSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.ADMIN);

        help += " <user> <type>";

    }

    @Override
    public void handleCommand(CommandSender s, String[] args) {

        if (args.length >= 2) {

            if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {

                String uuid = Bukkit.getPlayer(args[0]).getUniqueId().toString();
                int level = 1;

                if(args.length == 3){
                    try{
                        level = Integer.parseInt(args[2]);
                    }catch(Exception ex){}
                }

                long exp = Pet.getExpForLevel(level);
                if(level != 1)
                    exp++;

                if(args[1].equalsIgnoreCase("*")){
                    SmallPets.getInstance().getUserManager().giveUserAllPets(uuid, exp);
                    args[1] = "all";
                }else {
                    SmallPets.getInstance().getUserManager().giveUserPet(args[1].toLowerCase(), uuid, exp);
                }

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("givePetSender")
                        .replaceAll("%pet_type%", SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("pet." + args[1]))
                        .replaceAll("%player%", args[0]));

                Bukkit.getPlayer(args[0]).sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("givePetReceiver")
                        .replaceAll("%pet_type%", SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("pet." + args[1]))
                        .replaceAll("%sender%", s.getName()));

            } else {

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("playerIsntOnline"));

            }

        } else {

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
            options.add("*");

            List<String> finalOptions = options;
            SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().keySet().forEach(key -> finalOptions.add(key));

            options = finalOptions;

        }

        if(args.length == 3){
            options = new LinkedList<>();
            options.add("<level>");
        }

        return options;

    }
}
