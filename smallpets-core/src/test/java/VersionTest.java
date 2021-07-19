/*

Class created by SmallCode
19.07.2021 20:26

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.version.Version;
import it.smallcode.smallpets.core.version.VersionChecker;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static org.junit.Assert.*;

public class VersionTest {

    @Test
    public void testConstructor(){

        Version version = new Version(1, 0, 0, "dev");
        assertEquals("1.0.0-dev", version.toString());

        Version version1 = new Version("1.1.2-SNAPSHOT");
        assertEquals("1.1.2-SNAPSHOT", version1.toString());

        assertEquals(1, version1.getMainVersion());
        assertEquals(1, version1.getSubVersion());
        assertEquals(2, version1.getRevisionVersion());
        assertEquals("SNAPSHOT", version1.getType());

        Version version2 = new Version(1, 0, 0, null);
        assertEquals("1.0.0", version2.toString());

        Version version3 = new Version("1.1.2");
        assertEquals("1.1.2", version3.toString());

        assertEquals(1, version3.getMainVersion());
        assertEquals(1, version3.getSubVersion());
        assertEquals(2, version3.getRevisionVersion());
        assertEquals(null, version3.getType());

    }

    @Test
    public void testBigger(){
        Version version = new Version("1.0.0");
        Version version2 = new Version("1.0.0");
        assertEquals(false, version.bigger(version2));
        assertEquals(false, version2.bigger(version));

        version2 = new Version("2.0.0");
        assertEquals(false, version.bigger(version2));
        assertEquals(true, version2.bigger(version));

        version2 = new Version("1.1.0");
        assertEquals(false, version.bigger(version2));
        assertEquals(true, version2.bigger(version));

        version2 = new Version("1.0.1");
        assertEquals(false, version.bigger(version2));
        assertEquals(true, version2.bigger(version));

        version2 = new Version("0.9.5");
        assertEquals(true, version.bigger(version2));
        assertEquals(false, version2.bigger(version));

    }

    @Test
    public void testWrongVersionString(){
        assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                new Version("beta-6.2.4");
            }
        });
    }

    @Test
    public void testCheckSpigotVersion(){
        if(!SmallPetsCommons.DEBUG)
            assertEquals(false, VersionChecker.checkVersion("0.0.0"));
    }

}
