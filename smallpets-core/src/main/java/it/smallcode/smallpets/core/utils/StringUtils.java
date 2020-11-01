package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
01.11.2020 17:24

*/

public class StringUtils {

    public static String addLineBreaks(String string, int afterChars){

        StringBuilder sb = new StringBuilder(string);

        int i = 0;
        while ((i = sb.indexOf(" ", i + afterChars)) != -1) {
            sb.replace(i, i + 1, "\n");
        }

        return sb.toString();

    }

}
