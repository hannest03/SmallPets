package it.smallcode.smallpets.core;
/*

Class created by SmallCode
10.10.2020 16:29

*/

import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.*;
import it.smallcode.smallpets.core.utils.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class SmallPetsCommons {

    public static final boolean DEBUG = false;

    static {
        
        smallPetsCommons = new SmallPetsCommons();
        
    }

    private SmallPetsCommons(){}

    @Getter(AccessLevel.PUBLIC)
    private static SmallPetsCommons smallPetsCommons;

    private JavaPlugin javaPlugin;

    private String prefix;

    private boolean requirePermission;
    private boolean useProtocollib;
    private boolean useWorldGuard = false;
    private boolean showUnlockMessage = true;
    private boolean activateParticles = true;

    private List<String> disabledWorlds = new LinkedList<>();

    private List<String> petLore;

    private PetMapManager petMapManager;

    private UserManager userManager;
    //private AutoSaveManager autoSaveManager;
    private BackupManager backupManager;

    private InventoryManager inventoryManager;
    private InventoryCache inventoryCache;

    private ListenerManager listenerManager;
    private LanguageManager languageManager;

    private ExperienceManager experienceManager;
    private AbilityManager abilityManager;

    private SortManager sortManager = new SortManager();

    private INBTTagEditor INBTTagEditor;
    private IProtocolLibUtils protocolLibUtils;
    private IMetaDataUtils metaDataUtils;
    private IHealthModifierUtils healthModifierUtils;
    private ISpeedModifierUtils speedModifierUtils;

    private SkullCreator skullCreator;

}
