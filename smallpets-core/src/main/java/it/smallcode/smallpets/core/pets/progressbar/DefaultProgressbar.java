package it.smallcode.smallpets.core.pets.progressbar;
/*

Class created by SmallCode
24.06.2021 12:00

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.text.CenteredText;
import it.smallcode.smallpets.core.utils.LevelColorUtils;
import org.bukkit.ChatColor;

import java.util.LinkedList;
import java.util.List;

public class DefaultProgressbar implements Progressbar{

    @Override
    public String generateProgressbar(long exp, int level) {
        String bar = "";

        int bars = 35;

        long lastExp = SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(level);
        long nextExp = SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(level+1);

        if(level == 100){
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
        if(level == 100) {
            ret.add(SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("maxLevel"));
            return ret;
        }

        String colorCode = LevelColorUtils.getLevelColor(level);
        String progressBar = colorCode + level + " " + generateProgressbar(exp, level) + " " + colorCode + (level+1);

        ret.add(CenteredText.sendCenteredMessage(colorCode + level, ChatColor.stripColor(progressBar).length()));
        ret.add(progressBar);

        String expB = colorCode + (exp - SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(level)) + "§8/" + colorCode + (SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(level+1) - SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(level));
        ret.add(CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

        return ret;
    }
}
