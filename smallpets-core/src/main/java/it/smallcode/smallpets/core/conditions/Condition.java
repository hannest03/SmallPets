package it.smallcode.smallpets.core.conditions;
/*

Class created by SmallCode
02.08.2021 21:39

*/

import com.google.gson.JsonObject;

public interface Condition extends Cloneable{
    String getId();
    boolean isTrue();
    void load(JsonObject object);
}
