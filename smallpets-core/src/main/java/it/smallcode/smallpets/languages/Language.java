package it.smallcode.smallpets.languages;
/*

Class created by SmallCode
10.04.2020 16:03

*/

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;

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

        loadConfigurationSection("translations", Objects.requireNonNull(cfg.getConfigurationSection("translations")), cfg);

        System.out.println("Loaded: ");

        translations.forEach((s, s2) -> System.out.println(s + " | " + s2));

    }

    /**
     *
     * Loads a the language file recursively
     *
     * @param keyPre - the key before
     * @param configurationSection - the configuration section
     * @param cfg - the file configuration
     */
    private void loadConfigurationSection(String keyPre, ConfigurationSection configurationSection, FileConfiguration cfg){

        for(String key : configurationSection.getKeys(false)){

            if (cfg.get(keyPre + "." + key) instanceof ConfigurationSection) {

                loadConfigurationSection(keyPre + "." + key, Objects.requireNonNull(cfg.getConfigurationSection(keyPre + "." + key)), cfg);

            }else{

                System.out.println(keyPre + "." + key + " | " + cfg.getString(keyPre + "." + key));

                translations.put(keyPre + "." + key, cfg.getString(keyPre + "." + key));

            }

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

        name = "translations." + name;

        if(translations.containsKey(name)){

            return translations.get(name);

        }

        return name;

    }

    /**
     *
     * Returns a string from the file, if it doesn't exist the string is going to be empty
     *
     * @param name - the name of the field
     * @return The string of the field with the & replaced with §
     */

    public String getStringFormatted(String name){

        return ChatColor.translateAlternateColorCodes('&', getString(name));

    }

    public String getLanguageName(){

        return file.getName();

    }

    public HashMap<String, String> getTranslations() {
        return translations;
    }
}
