package it.smallcode.smallpets.core.abilities.templates;
/*

Class created by SmallCode
26.10.2020 20:44

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.utils.StringUtils;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InBiomeAbility extends Ability {

    private List<Biome> biomes;

    public InBiomeAbility(List<Biome> biomes){

        super(AbilityType.ABILITY);

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
