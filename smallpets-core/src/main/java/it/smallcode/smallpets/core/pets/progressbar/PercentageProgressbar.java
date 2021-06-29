package it.smallcode.smallpets.core.pets.progressbar;
/*

Class created by SmallCode
24.06.2021 16:36

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.text.CenteredText;
import it.smallcode.smallpets.core.utils.LevelColorUtils;
import org.bukkit.ChatColor;

import java.util.LinkedList;
import java.util.List;

public class PercentageProgressbar implements Progressbar{


    @Override
    public String generateProgressbar(long exp, int level) {
        String bar = "";

        int bars = 35;

        long lastExp = SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(level);
        long nextExp = SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(level+1);

        if(level == SmallPetsCommons.MAX_LEVEL){
            String colorCode = LevelColorUtils.getLevelColor(level);
            for(int i = 0; i < bars; i++) {
                bar += colorCode + "|";
            }
            return bar;
        }

        long oneBar = (nextExp - lastExp) / bars;
        long nextBar = 0;

        String colorCode = LevelColorUtils.getLevelColor(level);
        while(nextBar <= (exp- lastExp) && bar.length() < (bars*3)){
            nextBar += oneBar;
            bar += colorCode + "|";
        }
        while(bar.length() < (bars*3)){
            bar += "§8|";
        }
        return bar;
    }

    @Override
    public List<String> generateFullProgressbar(long exp, int level) {
        List<String> ret = new LinkedList<>();
        if(level == SmallPetsCommons.MAX_LEVEL) {
            String progressBar = generateProgressbar(exp, level);
            ret.add("§7" + level + "§8 " + progressBar + " §7100%");
            return ret;
        }

        String progressBar = generateProgressbar(exp, level);

        double xpNextLevel = SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(level+1);
        double xpCurrLevel = SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(level);

        double percentage = ((exp - xpCurrLevel) / (xpNextLevel - xpCurrLevel)) * 100;
        String percentageDisplay = (int)(percentage * 100) /100D + "%";

        ret.add("§7" + level + "§8 " + progressBar + " §7" + percentageDisplay);

        return ret;
    }
}
