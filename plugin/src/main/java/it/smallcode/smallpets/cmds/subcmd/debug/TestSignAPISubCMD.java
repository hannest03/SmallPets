package it.smallcode.smallpets.cmds.subcmd.debug;
/*

Class created by SmallCode
20.08.2021 19:47

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.signgui.SignGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TestSignAPISubCMD extends SubCommand {

    public TestSignAPISubCMD(String name, String permission) {
        super(name, permission, SubCommandType.DEBUG);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {
        if(s instanceof Player){
            SignGUI signGUI = new SignGUI.Builder(SmallPets.getInstance())
                    .setPlayer((Player) s)
                    .setLines(new String[4])
                    .setOnSignClosed((lines) -> {
                        String petName = Arrays.stream(lines).map(String::trim).collect(Collectors.joining());
                        s.sendMessage("New petname: " + petName);
                    }).create();
            signGUI.open();
        }
    }

}
