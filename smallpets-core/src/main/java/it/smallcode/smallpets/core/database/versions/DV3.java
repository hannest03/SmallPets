package it.smallcode.smallpets.core.database.versions;
/*

Class created by SmallCode
21.03.2022 21:57

*/

import it.smallcode.smallpets.core.database.Database;
import it.smallcode.smallpets.core.database.DatabaseVersion;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DV3 implements DatabaseVersion {
    @Override
    public int getVersion() {
        return 3;
    }

    @Override
    public void execute(Database database) throws SQLException {
        final String[] sqls = new String[]{
                "ALTER TABLE `pets` ADD `pname` VARCHAR(20);",
                "ALTER TABLE `pets` ADD `pnamespace` VARCHAR(20);"
        };

        for (String sql : sqls) {
            PreparedStatement statement = database.getConnection().prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        }
    }
}
