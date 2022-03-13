package it.smallcode.smallpets.core.manager.types;
/*

Class created by SmallCode
21.12.2020 18:09

*/

import java.util.HashMap;
import java.util.Map;

public class Settings {

    private boolean showPets = true;
    private Sort sort = Sort.NONE;

    public HashMap<String, Object> serialize(){
        HashMap<String, Object> data = new HashMap<>();

        data.put("showPets", isShowPets());
        data.put("sort", sort.name());

        return data;
    }

    public void unserialize(Map<String, Object> data){
        if(data.get("showPets") != null)
            setShowPets((Boolean) data.get("showPets"));

        if(data.get("sort") != null) {
            Sort sort = Sort.valueOf((String) data.get("sort"));
            if(sort != null)
                setSort(sort);
        }
    }

    public boolean isShowPets() {
        return showPets;
    }

    public void setShowPets(boolean showPets) {
        this.showPets = showPets;
    }

    public Sort getSort() {
        return sort;
    }
    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
