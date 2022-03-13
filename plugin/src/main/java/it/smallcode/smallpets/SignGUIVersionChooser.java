package it.smallcode.smallpets;
/*

Class created by SmallCode
20.08.2021 20:17

*/

import it.smallcode.smallpets.core.signgui.SignGUI;
import it.smallcode.smallpets.v1_12.SignGUIWrapper1_12;
import it.smallcode.smallpets.v1_13.SignGUIWrapper1_13;
import it.smallcode.smallpets.v1_15.SignGUIWrapper1_15;
import it.smallcode.smallpets.v1_16.SignGUIWrapper1_16;
import it.smallcode.smallpets.v1_17.SignGUIWrapper1_17;
import org.bukkit.Bukkit;

public class SignGUIVersionChooser {

    public static void selectVersion(){
        String version = Bukkit.getServer().getClass().getPackage().getName();
        version = version.substring(version.lastIndexOf('.'));
        version = version.replace(".v", "");
        if(version.startsWith("1_17")) {
            SignGUI.setVersionWrapper(new SignGUIWrapper1_17());
        }else if(version.startsWith("1_16")){
            SignGUI.setVersionWrapper(new SignGUIWrapper1_16());
        }else if(version.startsWith("1_15") || version.startsWith("1_14")){
            SignGUI.setVersionWrapper(new SignGUIWrapper1_15());
        }else if(version.startsWith("1_13")){
            SignGUI.setVersionWrapper(new SignGUIWrapper1_13());
        }else if(version.startsWith("1_12")){
            SignGUI.setVersionWrapper(new SignGUIWrapper1_12());
        }
    }

}