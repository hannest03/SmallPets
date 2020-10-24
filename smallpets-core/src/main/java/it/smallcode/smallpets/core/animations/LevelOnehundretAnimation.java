package it.smallcode.smallpets.core.animations;
/*

Class created by SmallCode
05.07.2020 14:05

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class LevelOnehundretAnimation {

    private int schedulerID;

    private int counter = 0;

    public LevelOnehundretAnimation(final Pet pet, LanguageManager languageManager, Plugin plugin){

        final ArrayList<String> colors = new ArrayList<>();

        colors.add("§4");
        colors.add("§c");
        colors.add("§6");
        colors.add("§e");
        colors.add("§2");
        colors.add("§a");
        colors.add("§b");
        colors.add("§3");
        colors.add("§1");
        colors.add("§9");
        colors.add("§d");
        colors.add("§5");

        schedulerID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            public void run() {

                int last = counter -1;

                if(last < 0)
                    last = colors.size()-1;

                int next = counter +1;

                if(next == colors.size())
                    next = 0;

                String name = "";

                if(pet.getOwner().getName().endsWith("s")){
                    name = languageManager.getLanguage().getStringFormatted("petNameFormatTwoS");
                }else {
                    name = languageManager.getLanguage().getStringFormatted("petNameFormat");
                }

                String level = colors.get(last) + "1" + colors.get(counter) + "0" + colors.get(next) + "0";

                name = name.replaceAll("%level%", level);
                name = name.replaceAll("%player_name%", pet.getOwner().getName());

                name += languageManager.getLanguage().getStringFormatted("pet." + pet.getID());

                pet.setCustomName(name);

                counter++;

                if(counter == colors.size())
                    counter = 0;

            }
        }, 0, 1);

    }

    public void cancel(){

        Bukkit.getScheduler().cancelTask(schedulerID);

    }

}
