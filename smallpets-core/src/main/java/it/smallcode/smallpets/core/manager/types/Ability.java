package it.smallcode.smallpets.core.manager.types;
/*

Class created by SmallCode
13.09.2020 17:42

*/

public abstract class Ability {

    private String id;

    public Ability(){}
    public Ability(String id){ this.id = id; }

    public abstract void registerListener();

    public String getId() {

        if(id == null || id.trim().length() == 0){ return "No ID!"; }

        return id;

    }
}
