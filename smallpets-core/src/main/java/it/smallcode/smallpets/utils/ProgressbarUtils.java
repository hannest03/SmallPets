package it.smallcode.smallpets.utils;
/*

Class created by SmallCode
27.10.2020 18:44

*/

import it.smallcode.smallpets.pets.Pet;
import org.bukkit.Color;

import java.util.ArrayList;

public class ProgressbarUtils {

    private static final ArrayList<String> colors;

    static{

        colors = new ArrayList<>();

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

    }

    public static String generateProgressBar(Pet pet){

        String bar = "";

        int bars = 35;

        long lastExp = pet.getExpForLevel(pet.getLevel());
        long nextExp = pet.getExpForNextLevel();

        if(pet.getLevel() == 100){

            int color = (int) (Math.random() * colors.size()-1);

            for(int i = 0; i < bars; i++) {

                bar += colors.get(color) + "|";

                color++;

                if(color == colors.size())
                    color = 0;

            }

            return bar;

        }

        long oneBar = (nextExp - lastExp) / bars;

        long nextBar = 0;

        while(nextBar <= (pet.getXp() - lastExp) && bar.length() < (bars*3)){

            nextBar += oneBar;

            bar += pet.getLevelColor() + "|";

        }

        while(bar.length() < (bars*3)){

            bar += "§8|";

        }

        return bar;

    }

}
