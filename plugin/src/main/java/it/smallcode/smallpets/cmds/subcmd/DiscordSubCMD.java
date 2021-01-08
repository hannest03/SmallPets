package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
30.08.2020 15:12

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DiscordSubCMD extends SubCommand {


    public DiscordSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.NONE);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        final String prefix = "§f[§dDiscord§7] §d";

        s.sendMessage(prefix + "Join the discord for the latest news on SmallPets!");
        s.sendMessage(prefix + SmallPets.DISCORD_LINK);

    }

}
