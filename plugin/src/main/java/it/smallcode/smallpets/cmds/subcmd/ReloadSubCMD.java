package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
15.07.2020 22:04

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.SmallPetsCommons;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ReloadSubCMD extends SubCommand {

    public ReloadSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.ADMIN);

        help += " <all/config/experienceTable/language/pets>";

    }

    @Override
    public List<String> handleAutoComplete(CommandSender s, String[] args) {

        List<String> options = super.handleAutoComplete(s, args);

        if(args.length == 1){

            options = new LinkedList<>();

            options.add("all");
            options.add("config");
            options.add("experienceTable");
            options.add("language");
            options.add("pets");

        }

        return options;
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(args.length == 1) {

            if (args[0].equalsIgnoreCase("all")) {

                SmallPets.getInstance().loadConfig();
                SmallPets.getInstance().getLanguageManager().loadLanguage(SmallPets.getInstance().getConfig().getString("language"));
                SmallPets.getInstance().getExperienceManager().reload();

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("reloaded")
                        .replaceAll("%type%", "all"));

            } else if (args[0].equalsIgnoreCase("config")) {

                SmallPets.getInstance().loadConfig();

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("reloaded")
                        .replaceAll("%type%", "config"));

            } else if (args[0].equalsIgnoreCase("language")) {

                SmallPets.getInstance().reloadConfig();
                SmallPets.getInstance().getLanguageManager().loadLanguage(SmallPets.getInstance().getConfig().getString("language"));

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("reloaded")
                        .replaceAll("%type%", "language"));

            }else if(args[0].equalsIgnoreCase("experienceTable")){

                SmallPets.getInstance().getExperienceManager().reload();

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("reloaded")
                        .replaceAll("%type%", "experienceTable"));

            }else if(args[0].equalsIgnoreCase("pets")){

                SmallPetsCommons.getSmallPetsCommons().getUserManager().saveUsers();
                SmallPetsCommons.getSmallPetsCommons().getUserManager().despawnPets();
                SmallPetsCommons.getSmallPetsCommons().getUserManager().getUsers().clear();

                if(SmallPetsCommons.getSmallPetsCommons().isRegisterCraftingRecipes()) {
                    SmallPetsCommons.getSmallPetsCommons().getPetManager().removeCraftingRecipe();
                }
                SmallPetsCommons.getSmallPetsCommons().getPetManager().getPetMap().clear();

                SmallPetsCommons.getSmallPetsCommons().getPetManager().registerPetClasses();
                SmallPetsCommons.getSmallPetsCommons().getPetManager().loadConfigPets();

                if(SmallPetsCommons.getSmallPetsCommons().isRegisterCraftingRecipes()) {
                    SmallPetsCommons.getSmallPetsCommons().getPetManager().registerCraftingRecipe();
                }

                for(Player all : Bukkit.getOnlinePlayers()) {
                    SmallPetsCommons.getSmallPetsCommons().getUserManager().loadUser(all.getUniqueId().toString());
                }
                SmallPetsCommons.getSmallPetsCommons().getUserManager().spawnPets();

                s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("reloaded")
                        .replaceAll("%type%", "pets"));

            }else{

                s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets admin " + getHelp());

            }

        } else {

            s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets admin " + getHelp());

        }

    }

}
