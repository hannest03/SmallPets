package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
04.07.2020 10:48

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SmallPetsTestCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if(s instanceof Player){

            Player p = (Player) s;

            if(args.length == 1){

                User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

                if(user != null){

                    try{

                        user.getSelected().giveExp(Integer.valueOf(args[0]), SmallPets.getInstance());

                    }catch (Exception ex){

                        p.sendMessage(SmallPets.getInstance().PREFIX + "Please enter an integer");

                    }

                }

            }else{

                s.sendMessage(SmallPets.getInstance().PREFIX + "/smallpetstest <exp>");

            }

        }else{

            s.sendMessage(SmallPets.getInstance().PREFIX + "This command is only for players");

        }

        return false;

    }
}
