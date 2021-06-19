package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
04.11.2020 11:27

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class GiveUnlockItemSubCMD extends SubCommand {


    public GiveUnlockItemSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.ADMIN);

        this.help += " <user> <pettype>";

    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(args.length == 2){

            Player p = Bukkit.getPlayer(args[0]);

            if(p != null && p.isOnline()){

                try {

                    SmallPetsCommons.getSmallPetsCommons().getUserManager().giveUserUnlockPetItem(args[1], p, 0L);

                    s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("givePetUnlockItemSender")
                            .replaceAll("%pet_type%", SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("pet." + args[1]))
                            .replaceAll("%player%", args[0]));

                    Bukkit.getPlayer(args[0]).sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("givePetUnlockItemReceiver")
                            .replaceAll("%pet_type%", SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("pet." + args[1]))
                            .replaceAll("%sender%", s.getName()));

                }catch (IllegalArgumentException ex){

                    s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("petNotRegistered"));

                }

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

            List<String> finalOptions = options;
            SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().keySet().forEach(key -> finalOptions.add(key));

            options = finalOptions;

        }

        return options;

    }

}
