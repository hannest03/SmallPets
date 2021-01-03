package it.smallcode.smallpets.cmds.subcmd.debug;
/*

Class created by SmallCode
02.01.2021 22:43

*/

import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestMetaDataSubCMD extends SubCommand {

    public TestMetaDataSubCMD(String name, String permission) {

        super(name, permission, SubCommandType.DEBUG);

        help += " <read/write/delete> <name>";

    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(s instanceof Player) {

            Player p = (Player) s;

            if (args.length == 2) {

                if (args[0].equalsIgnoreCase("read")) {

                    Block block = p.getTargetBlock(null , 5);

                    if(block != null){

                        String answer = SmallPetsCommons.getSmallPetsCommons().getMetaDataUtils().getMetaData(block, args[1]);

                        s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Read: " + answer);

                    }

                    return;

                }else if(args[0].equalsIgnoreCase("write")){

                    Block block = p.getTargetBlock(null , 5);

                    if(block != null){

                        SmallPetsCommons.getSmallPetsCommons().getMetaDataUtils().setMetaData(block, args[1], "Hello!");

                        s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Write!");

                    }

                    return;

                }else if(args[0].equalsIgnoreCase("delete")){

                    Block block = p.getTargetBlock(null , 5);

                    if(block != null){

                        SmallPetsCommons.getSmallPetsCommons().getMetaDataUtils().removeMetaData(block, args[1]);

                        s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Deleted!");

                    }

                    return;

                }

            }

            s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + getHelp());

        }

    }
}
