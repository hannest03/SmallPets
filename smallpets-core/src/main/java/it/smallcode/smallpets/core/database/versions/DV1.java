package it.smallcode.smallpets.core.database.versions;
/*

Class created by SmallCode
21.03.2022 21:41

*/

import it.smallcode.smallpets.core.database.Database;
import it.smallcode.smallpets.core.database.DatabaseVersion;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DV1 implements DatabaseVersion {
    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void execute(Database database) throws SQLException{
        final String[] sqls = new String[]{
                "CREATE TABLE IF NOT EXISTS users (\n" +
                        "\tuid VARCHAR(36) NOT NULL,\n" +
                        "\tpselected VARCHAR(36),\n" +
                        "\tPRIMARY KEY(uid)\n" +
                        ");",
                "CREATE TABLE IF NOT EXISTS settings (\n" +
                        "\tsname VARCHAR(100) NOT NULL,\n" +
                        "\tuid VARCHAR(36) NOT NULL,\n" +
                        "\tsvalue TEXT,\n" +
                        "\tPRIMARY KEY(sname, uid)\n" +
                        ");",
                "CREATE TABLE IF NOT EXISTS pets (\n" +
                        "\tpid VARCHAR(36) NOT NULL,\n" +
                        "\tptype TINYTEXT NOT NULL,\n" +
                        "\tpexp BIGINT DEFAULT 0,\n" +
                        "\tuid VARCHAR(36) NOT NULL,\n" +
                        "\tPRIMARY KEY(pid),\n" +
                        "\tFOREIGN KEY(uid)\n" +
                        "\t\tREFERENCES users(uid)\n" +
                        "\t\tON UPDATE CASCADE\n" +
                        "\t\tON DELETE CASCADE\n" +
                        ");",
                "CREATE TABLE IF NOT EXISTS infos (\n" +
                        "\tiname VARCHAR(100) NOT NULL,\n" +
                        "\tivalue TEXT,\n" +
                        "\tPRIMARY KEY(iname)\n" +
                        ");"
        };
        for (String sql : sqls) {
            PreparedStatement statement = database.getConnection().prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        }
    }
}
