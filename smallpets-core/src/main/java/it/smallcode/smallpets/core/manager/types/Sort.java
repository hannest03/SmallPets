package it.smallcode.smallpets.core.manager.types;
/*

Class created by SmallCode
19.06.2021 23:19

*/

public enum Sort {

    NONE("sort.none"),
    LEVEL_HIGHEST("sort.level_highest"),
    LEVEL_LOWEST("sort.level_lowest"),
    TYPE("sort.type"),
    NAME("sort.name");

    private String translationKey;

    Sort(String translationKey){
        this.translationKey = translationKey;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}
