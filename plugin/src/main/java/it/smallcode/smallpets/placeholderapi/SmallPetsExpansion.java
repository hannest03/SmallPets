package it.smallcode.smallpets.placeholderapi;
/*

Class created by SmallCode
02.08.2020 21:32

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.core.manager.types.User;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class SmallPetsExpansion extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "smallpets";
    }

    @Override
    public String getAuthor() {

        String authors = "";

        for(String author : SmallPets.getInstance().getDescription().getAuthors()){

            authors += author + ", ";

        }

        authors = authors.substring(0, authors.length() -2);

        return authors;
    }

    @Override
    public String getVersion() {
        return SmallPets.getInstance().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        switch (params){

            case "unlocked":{

                User user = SmallPets.getInstance().getUserManager().getUser(player.getUniqueId().toString());

                if(user != null){

                    return String.valueOf(user.getPets().size());

                }

                break;

            }

            case "selected":{

                User user = SmallPets.getInstance().getUserManager().getUser(player.getUniqueId().toString());

                if(user != null){

                    if(user.getSelected() != null) {

                        String name = user.getSelected().getName();

                        name = name.substring(0, 1).toUpperCase() + name.substring(1);

                        return name;

                    }

                    return "-";

                }

                break;

            }

            case "registeredPets":{

                return String.valueOf(SmallPets.getInstance().getPetMapManager().getPetMap().size());

            }

        }

        return null;

    }
}
