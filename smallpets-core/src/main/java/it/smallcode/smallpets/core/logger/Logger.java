package it.smallcode.smallpets.core.logger;
/*

Class created by SmallCode
23.03.2022 17:26

*/

public interface Logger {
    void println(String msg);
    default void info(String msg){
        println("INFO: " + msg);
    }
    default void warn(String msg){
        println("WARN: " + msg);
    }
    default void error(String msg){
        println("ERROR: " + msg);
    }
}
