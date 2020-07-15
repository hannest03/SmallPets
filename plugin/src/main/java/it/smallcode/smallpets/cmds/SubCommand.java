package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
15.07.2020 17:31

*/

import it.smallcode.smallpets.SmallPets;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    private String name;
    private String permission;

    public SubCommand(String name, String permission){

        this.name = name;
        this.permission = permission;

    }

    public void command(CommandSender s, String[] args){

        if(hasPermission(s)){

            handleCommand(s, args);

        }else{

            s.sendMessage(SmallPets.getInstance().PREFIX + "You haven't got the permission to do that!");

        }

    }

    public boolean hasPermission(CommandSender s){

        if(s.hasPermission(permission) || s.hasPermission("smallpets.*")){

            return true;

        }

        return false;

    }

    protected abstract void handleCommand(CommandSender s, String[] args);

    public abstract String getHelp();

    public String getName() {
        return name;
    }
}
