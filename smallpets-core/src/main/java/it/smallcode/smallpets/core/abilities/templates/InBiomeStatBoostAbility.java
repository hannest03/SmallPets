package it.smallcode.smallpets.core.abilities.templates;
/*

Class created by SmallCode
15.07.2021 12:08

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.utils.StringUtils;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public abstract class InBiomeStatBoostAbility extends StatBoostAbility{

    private List<Biome> biomes;

    public InBiomeStatBoostAbility(List<Biome> biomes, double maxExtraStat, double minExtraStat, NumberDisplayType numberDisplayType) {
        super(maxExtraStat, minExtraStat, numberDisplayType);
        this.biomes = biomes;
    }

    @Override
    public List<String> getAbilityTooltip(Pet pet) {
        List<String> lore = new ArrayList<>();
        lore.add("§6" + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage()
                .getStringFormatted("ability." + getID() + ".name"));
        String description = "§7" + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage()
                .getStringFormatted("ability." + getID() + ".description");
        String biomesString = "§6";
        for(Biome biome : biomes){
            biomesString += "§e" + biome.toString() + "§7, ";
        }
        biomesString = biomesString.substring(0, biomesString.length() -2);
        description = description.replaceAll("%biomes%", biomesString);
        description = StringUtils.addLineBreaks(description, 30);
        for(String s : description.split("\n"))
            lore.add(s);
        lore.add("");
        return lore;
    }

    public boolean containsBiome(Biome biome){
        return biomes.stream().anyMatch(biome1 -> biome1.compareTo(biome) == 0);
    }

}
