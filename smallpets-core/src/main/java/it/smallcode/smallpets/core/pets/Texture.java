package it.smallcode.smallpets.core.pets;
/*

Class created by SmallCode
02.08.2021 21:48

*/

import it.smallcode.smallpets.core.conditions.Condition;

public class Texture {

    private int priority;
    private String texture;
    private Condition[] conditions;

    public boolean areConditionsMet(){
        for(Condition condition : conditions){
            if(!condition.isTrue()) {
                return false;
            }
        }
        return true;
    }

    public int getPriority() {
        return priority;
    }

    public String getTexture() {
        return texture;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public void setConditions(Condition[] conditions) {
        this.conditions = conditions;
    }
}
