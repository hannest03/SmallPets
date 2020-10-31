package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
31.10.2020 15:50

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectSubCMD extends SubCommand {

    public SelectSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.NONE);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(s instanceof Player) {

            Player p = (Player) s;

            if (args.length == 1) {

                String type = args[0];

                if (SmallPets.getInstance().getPetMapManager().getPetMap().containsKey(type)) {

                    User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

                    if(user != null){

                        if(user.getPetFromType(type) != null){

                            user.setSelected(user.getPetFromType(type));

                            p.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("petSpawned"));

                        }else{

                            p.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("petNotUnlocked"));

                        }

                    }else{

                        p.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("userDataNotFound"));

                    }


                }else{

                    p.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("petNotUnlocked"));

                }

            }else{

                s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets select <type>");

            }

        } else {

            s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("commandIsOnlyForPlayers"));

        }

    }
}
