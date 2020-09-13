package it.smallcode.smallpets.core.manager.types;
/*

Class created by SmallCode
15.08.2020 10:28

*/

import it.smallcode.smallpets.core.pets.PetType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class ExperienceTable {

    private PetType petType;

    private HashMap<String, Integer> experienceTable;

    public ExperienceTable(){}

    public ExperienceTable(PetType petType, HashMap<String, Integer> experienceTable) {

        this.petType = petType;
        this.experienceTable = experienceTable;

    }

    public ExperienceTable unserialize(String id, HashMap<String, Integer> data){

        if(id != null){

            PetType petType = PetType.fromID(id);

            if(petType != null){

                this.setPetType(petType);

                if(data != null){

                    this.setExperienceTable(data);

                    return this;

                }

            }

        }

        return null;

    }

    public ExperienceTable unserialize(String id, ConfigurationSection data){

        if(id != null){

            PetType petType = PetType.fromID(id);

            if(petType != null){

                this.setPetType(petType);

                if(data != null){

                    this.setExperienceTable(configurationSectionToHashMap(data));

                    return this;

                }

            }

            Bukkit.getConsoleSender().sendMessage("§c§lCouldn't load " + id + " exp table!");

        }

        return null;

    }

    private HashMap<String, Integer> configurationSectionToHashMap(ConfigurationSection section){

        HashMap<String, Integer> data = new HashMap<>();

        for(String key : section.getKeys(false)){

            data.put(key, section.getInt(key));

        }

        return data;

    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public HashMap<String, Integer> getExperienceTable() {
        return experienceTable;
    }

    public void setExperienceTable(HashMap<String, Integer> experienceTable) {
        this.experienceTable = experienceTable;
    }
}
