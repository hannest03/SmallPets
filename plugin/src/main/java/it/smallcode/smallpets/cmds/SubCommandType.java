package it.smallcode.smallpets.cmds;
/*

Class created by SmallCode
30.08.2020 15:03

*/

public enum SubCommandType {

    ADMIN("admin", 1),
    NONE("", 0);

    private String name;
    private int minArgs;

    SubCommandType(String name, int minArgs){

        this.name = name;
        this.minArgs = minArgs;

    }

    public String getName() {
        return name;
    }

    public int getMinArgs() {
        return minArgs;
    }
}
