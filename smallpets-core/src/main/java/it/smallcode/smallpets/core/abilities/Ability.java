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
            name = ChatColor.translateAlternateColorCodes('&', name);
        }
        if(jsonObject.has("description")){
            description = jsonObject.get("description").getAsString();
            description = ChatColor.translateAlternateColorCodes('&', description);
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
}
