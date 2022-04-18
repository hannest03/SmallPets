package it.smallcode.smallpets.core.logger;
/*

Class created by SmallCode
23.03.2022 17:29

*/

import java.io.PrintStream;

public class LoggerFactory {
    public static Logger create(){
        return new BukkitLogger();
    }
    public static Logger create(PrintStream printStream){ return new ConsoleLogger(printStream); }
}
