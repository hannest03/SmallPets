package it.smallcode.smallpets.core.languages;
/*

Class created by SmallCode
10.04.2020 16:03

*/

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

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

        if(!file.getName().equalsIgnoreCase("en.yml")) {

            if (new File(file.getParentFile().getAbsolutePath(), "en.yml").exists()) {

                FileConfiguration cfg2 = YamlConfiguration.loadConfiguration(new File(file.getParentFile().getAbsolutePath(), "en.yml"));

                loadConfigurationSection("translations", Objects.requireNonNull(cfg2.getConfigurationSection("translations")), cfg2);

            }

        }

        loadConfigurationSection("translations", Objects.requireNonNull(cfg.getConfigurationSection("translations")), cfg);

    }

    /**
     *
     * Saves all the strings in the language file
     *
     */

    public void save(){

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        for(String key : translations.keySet()){

            cfg.set(key, translations.get(key));

        }

        try {

            cfg.save(file);

        } catch (IOException ex) {

            ex.printStackTrace();

        }

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
