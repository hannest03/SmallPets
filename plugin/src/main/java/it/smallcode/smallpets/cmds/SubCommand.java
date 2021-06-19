package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
15.07.2020 17:31

*/

import it.smallcode.smallpets.SmallPets;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class SubCommand {

    private String name;
    private String permission;

    protected String help;

    private SubCommandType subCommandType;

    public SubCommand(String name, String permission, SubCommandType subCommandType){

        this.name = name;
        this.permission = permission;

        this.subCommandType = subCommandType;

        help = getName();

    }

    public void command(CommandSender s, String[] args){

        if(hasPermission(s)){

            handleCommand(s, args);

        }else{

            s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("noPerms"));

        }

    }

    public boolean hasPermission(CommandSender s){
        if(permission == null)
            return true;

        if(s.hasPermission(permission) || s.hasPermission("smallpets.*")){
            return true;
        }
        return false;
    }

    public List<String> handleAutoComplete(CommandSender s, String[] args){

        if(args.length == 0)
            return new LinkedList<>(Collections.singleton(getName()));

        return null;

    }

    protected abstract void handleCommand(CommandSender s, String[] args);

    public String getHelp(){ return help; }

    public String getName() {
        return name;
    }

    public SubCommandType getSubCommandType() {
        return subCommandType;
    }
}
