package it.smallcode.smallpets.core;
/*

Class created by SmallCode
10.10.2020 16:29

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.*;
import it.smallcode.smallpets.core.utils.INBTTagEditor;
import it.smallcode.smallpets.core.utils.IProtocolLibUtils;
import it.smallcode.smallpets.core.utils.SkullCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class SmallPetsCommons {

    public static final boolean DEBUG = false;

    static {
        
        smallPetsCommons = new SmallPetsCommons();
        
    }
    
    private static SmallPetsCommons smallPetsCommons;

    private JavaPlugin javaPlugin;

    private String prefix;

    private boolean requirePermission;

    private PetMapManager petMapManager;

    private UserManager userManager;
    private AutoSaveManager autoSaveManager;

    private InventoryManager inventoryManager;
    private InventoryCache inventoryCache;

    private ListenerManager listenerManager;
    private LanguageManager languageManager;

    private ExperienceManager experienceManager;
    private AbilityManager abilityManager;

    private INBTTagEditor INBTTagEditor;
    private IProtocolLibUtils protocolLibUtils;

    private SkullCreator skullCreator;

    public static SmallPetsCommons getSmallPetsCommons() {
        return smallPetsCommons;
    }

    public PetMapManager getPetMapManager() {
        return petMapManager;
    }

    public void setPetMapManager(PetMapManager petMapManager) {
        this.petMapManager = petMapManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public void setInventoryManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public InventoryCache getInventoryCache() {
        return inventoryCache;
    }

    public void setInventoryCache(InventoryCache inventoryCache) {
        this.inventoryCache = inventoryCache;
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public void setListenerManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public void setLanguageManager(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    public ExperienceManager getExperienceManager() {
        return experienceManager;
    }

    public void setExperienceManager(ExperienceManager experienceManager) {
        this.experienceManager = experienceManager;
    }

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public void setAbilityManager(AbilityManager abilityManager) {
        this.abilityManager = abilityManager;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

    public void setJavaPlugin(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public INBTTagEditor getNbtTagEditor() {
        return INBTTagEditor;
    }

    public void setNbtTagEditor(INBTTagEditor INBTTagEditor) {
        this.INBTTagEditor = INBTTagEditor;
    }

    public IProtocolLibUtils getProtocolLibUtils() {
        return protocolLibUtils;
    }

    public void setProtocolLibUtils(IProtocolLibUtils protocolLibUtils) {
        this.protocolLibUtils = protocolLibUtils;
    }

    public SkullCreator getSkullCreator() {
        return skullCreator;
    }

    public void setSkullCreator(SkullCreator skullCreator) {
        this.skullCreator = skullCreator;
    }

    public boolean isRequirePermission() {
        return requirePermission;
    }

    public void setRequirePermission(boolean requirePermission) {
        this.requirePermission = requirePermission;
    }

    public AutoSaveManager getAutoSaveManager() {
        return autoSaveManager;
    }

    public void setAutoSaveManager(AutoSaveManager autoSaveManager) {
        this.autoSaveManager = autoSaveManager;
    }
}
