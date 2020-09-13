package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
15.08.2020 10:26

*/

import it.smallcode.smallpets.core.manager.types.ExperienceTable;
import it.smallcode.smallpets.core.pets.PetType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ExperienceManager {

    private static final String fileName = "experienceTable";

    private JavaPlugin plugin;

    private ArrayList<ExperienceTable> experienceTables;

    private HashMap<String, Integer> experienceTableAll;

    public ExperienceManager(JavaPlugin plugin){

        this.plugin = plugin;

        load();
        loadExperienceTableAll();

    }

    public void reload(){

        load();
        loadExperienceTableAll();

    }

    private void load(){

        experienceTables = new ArrayList<>();

        File file = new File(plugin.getDataFolder(), fileName + ".yml");

        file.getParentFile().mkdirs();

        if(!file.exists()){

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            writeDefaults();
            return;

        }

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        Set<String> keys = cfg.getKeys(false);

        for(String id : keys){

            ExperienceTable experienceTable = new ExperienceTable();

            ConfigurationSection section = (ConfigurationSection) cfg.get(id);

            experienceTables.add(experienceTable.unserialize(id, section));

        }

    }

    public PetType getPetTypeOfType(String type){

        for(ExperienceTable experienceTable : experienceTables){

            if(experienceTable.getExperienceTable().containsKey(type)){

                return experienceTable.getPetType();

            }

        }

        return null;

    }

    private void loadExperienceTableAll(){

        experienceTableAll = new HashMap<>();

        for(ExperienceTable experienceTable : experienceTables){

            for(String key : experienceTable.getExperienceTable().keySet()){

                experienceTableAll.put(key, experienceTable.getExperienceTable().get(key));

            }

        }

    }

    private void writeDefaults(){

        experienceTables = new ArrayList<>();

        ExperienceTable combatTable = new ExperienceTable();

        combatTable.setPetType(PetType.combat);

        HashMap<String, Integer> combatTableExp = new HashMap<>();

        combatTableExp.put("creeper", 10);
        combatTableExp.put("skeleton", 5);
        combatTableExp.put("spider", 5);
        combatTableExp.put("cave_spider", 5);
        combatTableExp.put("zombie", 5);
        combatTableExp.put("husk", 5);
        combatTableExp.put("drowned", 5);
        combatTableExp.put("slime", 1);
        combatTableExp.put("blaze", 5);
        combatTableExp.put("magma_cube", 5);
        combatTableExp.put("enderman", 15);
        combatTableExp.put("ghast", 30);
        combatTableExp.put("zombie_pigman", 10);
        combatTableExp.put("bat", 1);
        combatTableExp.put("guardian", 15);
        combatTableExp.put("shulker", 15);
        combatTableExp.put("evoker", 20);
        combatTableExp.put("vex", 10);
        combatTableExp.put("evocation_illager", 10);
        combatTableExp.put("vindication_illager", 10);
        combatTableExp.put("illusion_illager", 10);
        combatTableExp.put("wither_skeleton", 15);
        combatTableExp.put("phantom", 5);
        combatTableExp.put("elder_guardian", 500);

        combatTableExp.put("ender_dragon", 1000);
        combatTableExp.put("wither", 1500);
        combatTableExp.put("ender_crystal", 50);

        combatTableExp.put("sheep", 2);
        combatTableExp.put("pig", 2);
        combatTableExp.put("cow", 2);
        combatTableExp.put("chicken", 2);

        combatTableExp.put("rabbit", -2);
        combatTableExp.put("polar_bear", -10);
        combatTableExp.put("villager", -20);

        combatTable.setExperienceTable(combatTableExp);

        experienceTables.add(combatTable);

        ExperienceTable miningTable = new ExperienceTable();

        miningTable.setPetType(PetType.mining);

        HashMap<String, Integer> miningTableExp = new HashMap<>();

        miningTableExp.put("coal_ore", 5);
        miningTableExp.put("nether_quartz_ore", 5);
        miningTableExp.put("nether_gold_ore", 10);
        miningTableExp.put("iron_ingot", 15);
        miningTableExp.put("gold_ingot", 20);
        miningTableExp.put("lapis_ore", 10);
        miningTableExp.put("redstone_ore", 15);
        miningTableExp.put("diamond_ore", 30);
        miningTableExp.put("emerald_ore", 50);
        miningTableExp.put("ancient_debris", 200);

        miningTable.setExperienceTable(miningTableExp);

        experienceTables.add(miningTable);

        ExperienceTable foragingTable = new ExperienceTable();

        foragingTable.setPetType(PetType.foraging);

        HashMap<String, Integer> foragingTableExp = new HashMap<>();

        foragingTableExp.put("oak_log", 5);
        foragingTableExp.put("spruce_log", 5);
        foragingTableExp.put("birch_log", 5);
        foragingTableExp.put("jungle_log", 5);
        foragingTableExp.put("acacia_log", 5);
        foragingTableExp.put("dark_oak_log", 5);
        foragingTableExp.put("crimson_hyphae", 6);
        foragingTableExp.put("warped_hyphae", 6);

        foragingTable.setExperienceTable(foragingTableExp);

        experienceTables.add(foragingTable);

        ExperienceTable farmingTable = new ExperienceTable();

        farmingTable.setPetType(PetType.farming);

        HashMap<String, Integer> farmingTableExp = new HashMap<>();

        farmingTableExp.put("wheat", 5);
        farmingTableExp.put("potatoes", 5);
        farmingTableExp.put("carrots", 5);
        farmingTableExp.put("beetroots", 5);
        farmingTableExp.put("cocoa", 5);
        farmingTableExp.put("melon", 5);
        farmingTableExp.put("pumpkin", 5);
        farmingTableExp.put("cactus", 5);

        farmingTable.setExperienceTable(farmingTableExp);

        experienceTables.add(farmingTable);

        ExperienceTable fishingTable = new ExperienceTable();

        fishingTable.setPetType(PetType.fishing);

        HashMap<String, Integer> fishingTableExp = new HashMap<>();

        fishingTableExp.put("cod", 5);
        fishingTableExp.put("salmon", 5);
        fishingTableExp.put("tropical_fish", 5);
        fishingTableExp.put("pufferfish", 5);

        fishingTableExp.put("fishing_rod", 25);
        fishingTableExp.put("enchanted_book", 50);
        fishingTableExp.put("name_tag", 50);

        fishingTable.setExperienceTable(fishingTableExp);

        experienceTables.add(fishingTable);

        save();
        load();

    }

    private void save(){

        File file = new File(plugin.getDataFolder(), fileName + ".yml");

        file.getParentFile().mkdirs();

        if(!file.exists()){

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            writeDefaults();
            return;

        }

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        for(ExperienceTable experienceTable : experienceTables){

            cfg.set(experienceTable.getPetType().getId(), experienceTable.getExperienceTable());

        }

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<ExperienceTable> getExperienceTables() {
        return experienceTables;
    }

    public HashMap<String, Integer> getExperienceTableAll() {
        return experienceTableAll;
    }
}
