package it.smallcode.smallpets.languages;
/*

Class created by SmallCode
10.04.2020 16:03

*/

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Language {

    private File file;

    private HashMap<String, String> translations;

    /**
     *
     * Creates a Language
     *
     * @param file - the file of the language
     */

    public Language(File file){

        this.file = file;

        if(!file.exists()){

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        this.load();

    }

    /**
     *
     * Loads all the strings in the language file
     *
     */

    public void load(){

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        translations = new HashMap<>();

        for(String key: cfg.getConfigurationSection("translations").getKeys(false)) {
            translations.put(key, cfg.getString("translations." + key));
        }

    }

    /**
     *
     * Returns a string from the file, if it doesn't exist the string is going to be empty
     *
     * @param name - the name of the field
     * @return The string of the field
     */

    public String getString(String name){

        if(translations.containsKey(name)){

            return translations.get(name);

        }

        return "";

    }

    /**
     *
     * Returns a string from the file, if it doesn't exist the string is going to be empty
     *
     * @param name - the name of the field
     * @return The string of the field with the & replaced with §
     */

    public String getStringFormatted(String name){

        if(translations.containsKey(name)){

            return ChatColor.translateAlternateColorCodes('&', translations.get(name));

        }

        return name;

    }

    public String getLanguageName(){

        return file.getName();

    }

    public HashMap<String, String> getTranslations() {
        return translations;
    }
}
