package it.smallcode.smallpets.languages;
/*

Class created by SmallCode
10.04.2020 16:15

*/

import it.smallcode.smallpets.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Objects;

public class LanguageManager {

    private JavaPlugin plugin;
    private String prefix;
    private String selectedLanguage;

    private Language language;

    /**
     *
     * Creates the LanguageManager, loades the template languages and picks the language set in the config
     *
     */
    public LanguageManager(JavaPlugin plugin, String prefix, String selectedLanguage){

        this.plugin = plugin;
        this.prefix = prefix;
        this.selectedLanguage = selectedLanguage;

        loadLanguage();

    }

    public void loadLanguage(String newLanguage){

        setSelectedLanguage(newLanguage);

        Bukkit.getConsoleSender().sendMessage("§b" + newLanguage);
        Bukkit.getConsoleSender().sendMessage("§b" + selectedLanguage);

        loadLanguage();

    }

    public void loadLanguage(){

        if(!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdirs();

        File dir = new File(plugin.getDataFolder().getAbsolutePath() + "/languages/");

        if(!dir.exists())
            dir.mkdirs();

        Bukkit.getConsoleSender().sendMessage(prefix + "Loading language " + selectedLanguage + " ...");

        File enFile = new File(dir.getAbsolutePath(), "en.yml");

        if(!enFile.exists()){

            FileUtils.insertData("en.yml", dir.getAbsoluteFile() + "/en.yml", plugin);

        }

        updateFile(enFile);

        File deFile = new File(dir.getAbsolutePath(), "de.yml");

        if(!deFile.exists()){

            FileUtils.insertData("de.yml", dir.getAbsoluteFile() + "/de.yml", plugin);

        }

        updateFile(deFile);

        File itFile = new File(dir.getAbsolutePath(), "it.yml");

        if(!itFile.exists()){

            FileUtils.insertData("it.yml", dir.getAbsoluteFile() + "/it.yml", plugin);

        }

        updateFile(itFile);

        for(File file : dir.listFiles()){

            if(file.getName().equals(selectedLanguage + ".yml")){

                language = new Language(file);

            }

        }

        if(language == null){

            Bukkit.getConsoleSender().sendMessage(prefix + "Language " + selectedLanguage + " not found! Using english!");

            language = new Language(new File(dir.getAbsolutePath(), "en.yml"));

        }

        Bukkit.getConsoleSender().sendMessage(prefix + "Language set to " + language.getLanguageName() + "!");

    }

    private void updateFile(File file){

        Reader reader = new InputStreamReader(Objects.requireNonNull(plugin.getResource(file.getName())));

        YamlConfiguration newCfg = YamlConfiguration.loadConfiguration(reader);

        YamlConfiguration oldCfg = YamlConfiguration.loadConfiguration(file);

        if(newCfg.getConfigurationSection("translations") != null) {

            for (String key : newCfg.getConfigurationSection("translations").getKeys(false)) {

                if (!oldCfg.contains("translations." + key)) {

                    oldCfg.set("translations." + key, newCfg.get("translations." + key));

                }

            }

        }

        try {
            oldCfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * Returns the language object
     *
     * @return - the language
     */

    public Language getLanguage() {
        return language;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }
}
