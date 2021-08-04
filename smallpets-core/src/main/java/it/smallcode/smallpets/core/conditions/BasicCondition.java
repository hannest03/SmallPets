package it.smallcode.smallpets.core.conditions;
/*

Class created by SmallCode
02.08.2021 21:39

*/

import com.google.gson.JsonObject;

public class BasicCondition implements Condition{

    public static final String id = "basic_condition";

    private boolean returnValue = true;

    public void setReturnValue(boolean returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public boolean isTrue() {
        return returnValue;
    }

    @Override
    public void load(JsonObject object) {
        if(object.has("returnValue")){
            setReturnValue(object.get("returnValue").getAsBoolean());
        }
    }

    @Override
    public String getId() {
        return id;
    }
}
