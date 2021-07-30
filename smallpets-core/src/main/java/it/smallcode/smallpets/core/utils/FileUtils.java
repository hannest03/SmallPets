package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
10.04.2020 21:17

*/

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.smallcode.smallpets.core.manager.PetManager;
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

    public static JsonObject loadToJson(String content){
        Gson gson = new Gson();
        return gson.fromJson(content, JsonObject.class);
    }

    public static JsonObject loadToJson(File file){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while((line = reader.readLine()) != null) stringBuilder.append(line);
            return loadToJson(stringBuilder.toString());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
