package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
30.08.2020 15:15

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DonateSubCMD extends SubCommand {

    public DonateSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.NONE);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        final String prefix = "§e[§6Donate§e] ";

        s.sendMessage(prefix + "With this link you can support the development of SmallPets!");
        s.sendMessage(prefix + SmallPets.DONATION_LINK);

    }

}
