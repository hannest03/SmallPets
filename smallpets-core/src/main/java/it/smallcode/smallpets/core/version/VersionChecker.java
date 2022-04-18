package it.smallcode.smallpets.core.version;
/*

Class created by SmallCode
19.07.2021 21:07

*/

import it.smallcode.smallpets.core.SmallPetsCommons;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class VersionChecker {

    private static final int RESOURCE_ID = 81184;

    public static boolean checkVersion(String currentVersionString){
        if(SmallPetsCommons.DEBUG)
            return true;
        Version currentVersion = new Version(currentVersionString);
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + RESOURCE_ID).openStream(); Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                String versionString = scanner.next();
                Version version = new Version(versionString);
                //People with devbuild should also get update notification
                if(version.equals(currentVersion) && currentVersion.getType() != null && currentVersion.getType().equals("dev"))
                    return true;
                return version.bigger(currentVersion);
            }
        } catch (Exception exception) {
            SmallPetsCommons.getSmallPetsCommons().getLogger().error( "Cannot look for updates: " + exception.getMessage());
        }
        return false;
    }

}
