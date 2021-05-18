package it.smallcode.smallpets.api.exceptions;
/*

Class created by SmallCode
18.05.2021 16:04

*/

public class NoSuchPetTypeException extends Exception{

    private String type;

    public NoSuchPetTypeException(String type){

        super("There was no pet with the id " + type + " registered!");

        this.type = type;

    }

    public String getType() {
        return type;
    }
}
