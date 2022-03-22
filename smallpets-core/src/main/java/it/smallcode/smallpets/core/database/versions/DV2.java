package it.smallcode.smallpets.core.database.versions;
/*

Class created by SmallCode
21.03.2022 21:57

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.database.Database;
import it.smallcode.smallpets.core.database.DatabaseVersion;
import it.smallcode.smallpets.core.manager.PetManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DV2 implements DatabaseVersion {
    @Override
    public int getVersion() {
        return 2;
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

        // UPDATE EXISTING TYPE (NAMESPACE)
        setExistingPetNamespaces(database.getConnection());
    }

    private void setExistingPetNamespaces(Connection connection) throws SQLException {
        final String selectSql = "SELECT * FROM pets;";
        final String updateSql = "UPDATE pets SET pnamespace = ?, ptype = ? WHERE pid = ? AND uid = ?;";

        PreparedStatement statement = connection.prepareStatement(selectSql);

        ResultSet result = statement.executeQuery();
        while(result.next()){
            String pid = result.getString("pid");
            String uid = result.getString("uid");
            String type = result.getString("ptype");

            // if smallpets:tiger => split
            String[] types = type.split(":");
            if(types.length == 2){
                String namespace = types[0];
                String id = types[1];

                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, namespace);
                updateStatement.setString(2, id);
                updateStatement.setString(3, pid);
                updateStatement.setString(4, uid);
                updateStatement.executeUpdate();
            }
        }
    }
}
