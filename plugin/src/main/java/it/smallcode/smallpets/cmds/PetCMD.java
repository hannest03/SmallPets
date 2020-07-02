package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
02.07.2020 15:25

*/

import it.smallcode.smallpets.SmallPets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PetCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if(s instanceof Player){

            Player p = (Player) s;

            SmallPets.getInstance().getPetManager().spawnPet("tiger", p, 0);

            p.sendMessage(SmallPets.getInstance().PREFIX + "Spawned a tiger!");

        }else{

            s.sendMessage(SmallPets.getInstance().PREFIX + "This command is only for players");

        }

        return false;

    }
}
