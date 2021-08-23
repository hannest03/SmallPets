package it.smallcode.smallpets;
/*

Class created by SmallCode
20.08.2021 20:17

*/

import it.smallcode.smallpets.core.signgui.SignGUI;
import it.smallcode.smallpets.v1_17.SignGUIWrapper1_17;

public class SignGUIVersionChooser {

    public static void selectVersion(){
        SignGUI.setVersionWrapper(new SignGUIWrapper1_17());
    }

}
