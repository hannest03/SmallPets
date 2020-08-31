package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
30.08.2020 15:12

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import org.bukkit.command.CommandSender;

public class DiscordSubCMD extends SubCommand {


    public DiscordSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.NONE);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        s.sendMessage("§f[§dDiscord§7] §d" + SmallPets.DISCORD_LINK);

    }

}
