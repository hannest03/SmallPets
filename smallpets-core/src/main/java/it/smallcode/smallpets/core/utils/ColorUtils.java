package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
01.09.2021 20:38

*/

import net.md_5.bungee.api.ChatColor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {

    private static final String HEX_COLOR_PATTERN = "&#([a-fA-F0-9]{6})";

    private static final Pattern PATTERN = Pattern.compile(HEX_COLOR_PATTERN);

    public static String translateColors(String string){
        try {
            Matcher matcher = PATTERN.matcher(string);
            while (matcher.find()) {
                String color = string.substring(matcher.start(), matcher.end());
                String colorCode = color.substring(1);

                Method method = ChatColor.class.getMethod("of", String.class);
                ChatColor chatColor = (ChatColor) method.invoke(null, colorCode);

                string = string.replaceAll(color, chatColor.toString());
                matcher = PATTERN.matcher(string);
            }
        }catch (NoSuchMethodError | NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored){}

        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
