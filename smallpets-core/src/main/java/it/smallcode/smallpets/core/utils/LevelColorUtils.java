package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
22.06.2021 20:39

*/

import java.util.ArrayList;

public class LevelColorUtils {

    protected static final ArrayList<String> levelColors = new ArrayList<>();

    static {

        levelColors.add("§7");
        levelColors.add("§2");
        levelColors.add("§a");
        levelColors.add("§e");
        levelColors.add("§6");
        levelColors.add("§c");
        levelColors.add("§4");
        levelColors.add("§d");
        levelColors.add("§b");
        levelColors.add("§f");

    }

    /**
     *
     * Returns the level color of the pet
     *
     * @return the color
     */

    public static String getLevelColor(int level){

        int index = level / 10;

        if(levelColors.size() <= index)
            return "";

        return levelColors.get(index);

    }

}
