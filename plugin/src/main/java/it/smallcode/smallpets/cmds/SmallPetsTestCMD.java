package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
04.07.2020 10:48

*/

import it.smallcode.smallpets.SmallPets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SmallPetsTestCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if(s instanceof Player){

            Player p = (Player) s;

            SmallPets.getInstance().getUserManager().giveUserPet("tiger", p.getUniqueId().toString());

        }else{

            s.sendMessage(SmallPets.getInstance().PREFIX + "This command is only for players");

        }

        return false;

    }
}
