package it.smallcode.smallpets.cmds.subcmd;
/*

Class created by SmallCode
31.10.2020 15:50

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.cmds.SmallPetsCMD;
import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SelectSubCMD extends SubCommand {

    public SelectSubCMD(String name, String permission) {

        super(name, permission, SubCommandType.NONE);

        help += " <uuid>";

    }

    @Override
    public List<String> handleAutoComplete(CommandSender s, String[] args) {

        List<String> options = super.handleAutoComplete(s, args);

        if(!(s instanceof Player))
            return null;

        if(args.length == 0)
            return new LinkedList<String>(Collections.singleton(getName()));

        if(args.length == 1){

            options = new LinkedList<>();
            List<String> petOptions = new LinkedList<>();

            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(((Player) s).getUniqueId().toString());

            user.getPets().forEach(pet -> petOptions.add(pet.getUuid().toString()));

            options.addAll(petOptions);

        }

        return options;

    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {

        if(s instanceof Player) {

            Player p = (Player) s;
            if(SmallPetsCommons.getSmallPetsCommons().getDisabledWorlds().contains(p.getLocation().getWorld().getName())) {
                p.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("pets_disabled_world"));
                return;
            }

            if (args.length == 1) {

                UUID petUUID = UUID.fromString(args[0]);

                User user = SmallPets.getInstance().getUserManager().getUser(p.getUniqueId().toString());

                if(user != null){

                    if(user.getPetFromUUID(petUUID) != null){

                        user.setSelected(user.getPetFromUUID(petUUID));

                        p.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("petSpawned"));

                    }else{

                        p.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("petNotUnlocked"));

                    }

                }else{

                    p.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("userDataNotFound"));

                }

            }else{

                s.sendMessage(SmallPets.getInstance().getPrefix() + "/smallpets select <type>");

            }

        } else {

            s.sendMessage(SmallPets.getInstance().getPrefix() + SmallPets.getInstance().getLanguageManager().getLanguage().getStringFormatted("commandIsOnlyForPlayers"));

        }

    }
}
