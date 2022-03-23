package it.smallcode.smallpets.core.database;

import java.sql.SQLException;

public interface DatabaseVersion {
    int getVersion();
    void execute(Database database) throws SQLException;
}
