package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
10.04.2020 21:17

*/

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    public static void insertData(String from, String to, JavaPlugin plugin) {

        File file = new File(to);
        file.delete();

        try (InputStream localInputStream = plugin.getResource(from)) {

            if (localInputStream != null) {

                if (Paths.get(to) != null) {

                    Files.copy(localInputStream, Paths.get(to), StandardCopyOption.REPLACE_EXISTING);

                } else {

                    System.out.println("Path to is null");

                }

            } else {

                System.out.println("InputStream is null for " + from);

            }

        } catch (IOException ex) {
        }

    }

}
