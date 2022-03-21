package it.smallcode.smallpets.core.database.versions;
/*

Class created by SmallCode
21.03.2022 21:44

*/

import it.smallcode.smallpets.core.database.Database;
import it.smallcode.smallpets.core.database.DatabaseVersion;

import java.sql.SQLException;

public class DV2 implements DatabaseVersion {
    @Override
    public int getVersion() {
        return 2;
    }

    @Override
    public void execute(Database database) throws SQLException {
        System.out.println("UPGRADE DATABASE");
    }
}
