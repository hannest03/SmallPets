package it.smallcode.smallpets.core.abilities.templates;
/*

Class created by SmallCode
17.08.2021 11:46

*/

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.utils.StringUtils;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public abstract class InBiomeStatBoostAbility extends StatBoostAbility{

    private List<Biome> biomes = new LinkedList<>();

    @Override
    public void addBoost(Player p, Ability ability) {
        StatBoostAbility statBoostAbility = (StatBoostAbility) ability;

        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());
        if(user == null)
            return;
        double boost = statBoostAbility.getStatBoost(user.getSelected().getLevel());
        SmallPetsCommons.getSmallPetsCommons().getSpeedModifierUtils().addModifier(p, ability.getID(), boost, AttributeModifier.Operation.ADD_NUMBER);
        debug(getID() + " add modifier " + boost);
    }

    @Override
    public void removeBoost(Player p, Ability ability) {
        SmallPetsCommons.getSmallPetsCommons().getSpeedModifierUtils().removeModifier(p, ability.getID());
        debug(getID() + " remove modifier");
    }

    @Override
    public void load(JsonObject jsonObject) {
        super.load(jsonObject);
        if(jsonObject.has("biomes")){
            JsonArray biomesArray = jsonObject.get("biomes").getAsJsonArray();
            for(int i = 0; i < biomesArray.size(); i++){
                JsonElement element = biomesArray.get(i);
                if(element != null){
                    try {
                        String biomeName = element.getAsString();
                        Biome biome = Biome.valueOf(biomeName);
                        biomes.add(biome);
                    }catch(Exception ex){

                    }
                }
            }
        }
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

    public void setBiomes(List<Biome> biomes) {
        this.biomes = biomes;
    }

    public boolean containsBiome(Biome biome){
        return biomes.stream().anyMatch(biome1 -> biome1.compareTo(biome) == 0);
    }

}
