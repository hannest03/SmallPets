package it.smallcode.smallpets.core.manager.types;
/*

Class created by SmallCode
21.12.2020 18:09

*/

import java.util.HashMap;
import java.util.Map;

public class Settings {

    private boolean showPets = true;

    public HashMap<String, Object> serialize(){

            HashMap<String, Object> data = new HashMap<>();

            data.put("showPets", isShowPets());

            return data;

    }

    public void unserialize(Map<String, Object> data){

        if(data.get("showPets") != null)
            setShowPets((Boolean) data.get("showPets"));

    }

    public boolean isShowPets() {
        return showPets;
    }

    public void setShowPets(boolean showPets) {
        this.showPets = showPets;
    }
}
