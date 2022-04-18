package it.smallcode.smallpets.core.logger;
/*

Class created by SmallCode
23.03.2022 17:36

*/

import java.io.PrintStream;

public class ConsoleLogger implements Logger{
    private final PrintStream printStream;

    public ConsoleLogger(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void println(String msg) {
        printStream.println("[SmallPets] " + msg);
    }
}
