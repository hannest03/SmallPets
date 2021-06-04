package it.smallcode.smallpets.core.worldguard;
/*

Class created by SmallCode
21.02.2021 16:31

*/

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.DoubleFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

public class SmallFlags {

    public static StateFlag GIVE_EXP = null;
    public static StateFlag SHOW_PETS = null;
    public static StateFlag ALLOW_ABILITIES = null;

    public static DoubleFlag EXP_MODIFIER = null;

    public static void registerFlags(){

        GIVE_EXP = registerStateFlag("smallpets-giveExp", true);
        SHOW_PETS = registerStateFlag("smallpets-showPets", true);
        ALLOW_ABILITIES = registerStateFlag("smallpets-allowAbilities", true);

        EXP_MODIFIER = registerDoubleFlag("smallpets-expModifier", 1D);

    }

    private static StateFlag registerStateFlag(String name, boolean defaultValue){

        try {

            FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

            StateFlag flag = new StateFlag(name, defaultValue);
            registry.register(flag);

            return flag;

        }catch(Exception ex){

            ex.printStackTrace();

            return null;

        }

    }

    private static DoubleFlag registerDoubleFlag(String name, double defaultValue){

        try {

            FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

            final Number[] suggestedValues = { defaultValue };

            DoubleFlag flag = new DoubleFlag(name);

            try {

                if(DoubleFlag.class.getMethod("setSuggestedValues", Number[].class) != null)
                    flag.setSuggestedValues(suggestedValues);

            }catch(Exception ex){}

            registry.register(flag);

            return flag;

        }catch(Exception ex){

            ex.printStackTrace();

            return null;

        }

    }

}
