package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
12.03.2022 12:59

*/

import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.convert.ConversionManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Consumer;

public class ConvertSubCMD extends SubCommand {

    public ConvertSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.ADMIN);
    }

    @Override
    protected void handleCommand(final CommandSender s, String[] args) {
        if(s instanceof Player) {
            s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("commandIsNotForPlayers"));
            return;
        }

        if(Bukkit.getOnlinePlayers().size() != 0){
            s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("serverMustBeEmpty"));
            return;
        }
        Consumer<ConversionManager.ConversionResult> consumer = conversionResult -> {
            String response = "  ";

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(conversionResult.user.getUuid()));
            switch(conversionResult.event){
                case LOADED:{
                    response += SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("convert.success");
                    response = response.replaceAll("%name%", offlinePlayer.getName());
                    break;
                }
                case FAILED:{
                    response += SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("convert.failed");
                    response = response.replaceAll("%name%", offlinePlayer.getName());
                    response = response.replaceAll("%uuid%", offlinePlayer.getUniqueId().toString());
                    break;
                }
            }
            s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + response);
        };

        s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("convert.start"));
        ConversionManager.convertFromFile(consumer);
        s.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("convert.end"));
    }
}
