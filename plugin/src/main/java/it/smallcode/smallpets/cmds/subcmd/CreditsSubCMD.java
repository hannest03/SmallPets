package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
11.12.2020 10:36

*/

import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CreditsSubCMD extends SubCommand {

    public CreditsSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.NONE);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        String prefix = SmallPetsCommons.getSmallPetsCommons().getPrefix();

        s.sendMessage("");
        s.sendMessage(prefix + "§bDeveloper§8: ");
        s.sendMessage(prefix + "§7 -SmallCode");
        s.sendMessage("");
        s.sendMessage(prefix + "§6Translators§8: ");
        s.sendMessage(prefix + "§7 -Kaspian");
        s.sendMessage(prefix + "§7 -Erkutay");
        s.sendMessage(prefix + "§7 -OcThomas");
        s.sendMessage(prefix + "§7 -IamRymatics");
        s.sendMessage(prefix + "§7 -WildSquirrel");
        s.sendMessage(prefix + "§7 -vgtom4");
        s.sendMessage(prefix + "§7 -Plebexer");
        s.sendMessage(prefix + "§7 -SwiftlyMC");
        s.sendMessage(prefix + "§7 -Settlo");
        s.sendMessage(prefix + "§7 -LibbyClaW");
        s.sendMessage("");
        s.sendMessage(prefix + "§aDonator§8: ");
        s.sendMessage(prefix + "§7 -Eden");
        s.sendMessage("");

    }
}
