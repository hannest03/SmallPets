package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
02.07.2020 15:25

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.manager.types.User;
import it.smallcode.smallpets.pets.Pet;
import it.smallcode.smallpets.pets.v1_15.pets.Penguin;
import it.smallcode.smallpets.pets.v1_15.pets.Tiger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SmallPetsCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if(s instanceof Player){

            Player p = (Player) s;

            User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

            if(user != null) {

                List<Pet> pets =user.getPets();

                SmallPets.getInstance().getInventoryManager().openPetsMenu(pets, p);

            }else{

                p.sendMessage(SmallPets.getInstance().PREFIX + "Your user data couldn't be found!");

            }

        }else{

            s.sendMessage(SmallPets.getInstance().PREFIX + "This command is only for players");

        }

        return false;

    }
}
