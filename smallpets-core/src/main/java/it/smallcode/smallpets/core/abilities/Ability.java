package it.smallcode.smallpets.core.abilities;
/*

Class created by SmallCode
13.09.2020 17:42

*/

import com.google.gson.JsonObject;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Data
public abstract class Ability {

    private String name = "placeholder";
    private String description = "placeholder";

    private AbilityType abilityType = AbilityType.HIDDEN;

    public Ability(){}

    public void load(JsonObject jsonObject){
        if(jsonObject.has("ability_type")){
            abilityType = AbilityType.valueOf(jsonObject.get("ability_type").getAsString().toUpperCase(Locale.ROOT));
        }
        //TODO: Replace with translation and stats (if needed)
        if(jsonObject.has("name")){
            name = jsonObject.get("name").getAsString();
        }
        if(jsonObject.has("description")){
            description = jsonObject.get("description").getAsString();
        }
    }

    public abstract void addBoost(Player p, Ability ability);
    public abstract void removeBoost(Player p, Ability ability);

    public abstract List<String> getAbilityTooltip(Pet pet);

    protected void debug(String msg){
        if(SmallPetsCommons.DEBUG)
            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cDEBUG: " + getID() + "§7 " + msg);
    }

    public String getID(){ return SmallPetsCommons.getSmallPetsCommons().getAbilityManager().getIDByClass(this.getClass()); }

    public String getName() {
        String name = this.name;
        String translationKey;
        do{
            int firstIndex = name.indexOf("{{");
            int secondIndex = name.indexOf("}}");
            if(firstIndex == -1 || secondIndex == -1){
                translationKey = null;
            }else {
                translationKey = name.substring(name.indexOf("{{") + 2, name.indexOf("}}"));
                String translation = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getString(translationKey);
                String replaceKey =  Pattern.quote("{{" + translationKey + "}}");
                name = name.replaceAll(replaceKey, translation);
            }
        }while(translationKey != null);
        return ChatColor.translateAlternateColorCodes('&', name);
    }

    public String getDescription() {
        String description = this.description;
        String translationKey;
        do{
            int firstIndex = description.indexOf("{{");
            int secondIndex = description.indexOf("}}");
            if(firstIndex == -1 || secondIndex == -1){
                translationKey = null;
            }else {
                translationKey = description.substring(description.indexOf("{{") + 2, description.indexOf("}}"));
                String translation = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getString(translationKey);
                String replaceKey =  Pattern.quote("{{" + translationKey + "}}");
                description = description.replaceAll(replaceKey, translation);
            }
        }while(translationKey != null);
        return ChatColor.translateAlternateColorCodes('&', description);
    }

}
