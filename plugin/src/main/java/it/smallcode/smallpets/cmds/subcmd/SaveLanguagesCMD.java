package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
03.11.2020 16:48

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.languages.Language;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.List;

public class SaveLanguagesCMD extends SubCommand {


    public SaveLanguagesCMD(String name, String permission) {
        super(name, permission, SubCommandType.ADMIN);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(!SmallPets.getInstance().getDataFolder().exists())
            SmallPets.getInstance().getDataFolder().mkdirs();

        File dir = new File(SmallPets.getInstance().getDataFolder().getAbsolutePath() + "/languages/");

        if(!dir.exists())
            dir.mkdirs();

        for(File file : dir.listFiles()){

            if(file.isFile()){

                Language language = new Language(file);

                s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("savingLanguage").replaceAll("%language%", language.getLanguageName()));

                language.save();

                s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("savedLanguage").replaceAll("%language%", language.getLanguageName()));
                s.sendMessage("");

            }

        }

    }
}
