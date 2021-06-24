package it.smallcode.smallpets.cmds.subcmd.debug;
/*

Class created by SmallCode
23.06.2021 21:02

*/

import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpawnNewTestPetSystemSubCMD extends SubCommand {

    public SpawnNewTestPetSystemSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.DEBUG);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {
        if(s instanceof Player){
            Player p = (Player) s;
            Pet pet = PetFactory.createPet("test", UUID.randomUUID(), p, 0L);
            pet.spawn();
            p.sendMessage("Should have spawned");
        }
    }
}
