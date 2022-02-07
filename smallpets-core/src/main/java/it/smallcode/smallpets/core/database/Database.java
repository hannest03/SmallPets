package it.smallcode.smallpets.core.database;
/*

Class created by SmallCode
07.02.2022 20:19

*/

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private Connection connection;

    public void connect(String filePath) throws SQLException{
        try {
            if(connection != null && !connection.isClosed()) return;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        File file = new File(filePath);
        if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        createTables();
    }

    public void disconnect(){
        try {
            if(connection == null || connection.isClosed()) return;
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        final String sql = "\n" +
                "CREATE TABLE IF NOT EXISTS users (\n" +
                "\tuid VARCHAR(36) NOT NULL,\n" +
                "\tpselected VARCHAR(36),\n" +
                "\tPRIMARY KEY(uid)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE IF NOT EXISTS settings (\n" +
                "\tsname VARCHAR(100) NOT NULL,\n" +
                "\tuid VARCHAR(36) NOT NULL,\n" +
                "\tsvalue TEXT,\n" +
                "\tPRIMARY KEY(sname, uid)\n" +
                ");\n" +
                "\n" +
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
                ");\n" +
                "\n" +
                "ALTER TABLE users\n" +
                "\tADD FOREIGN KEY(pselected)\n" +
                "\t\t\tREFERENCES pets(pid)\n" +
                "\t\t\tON UPDATE CASCADE\n" +
                "\t\t\tON DELETE SET NULL;";

        PreparedStatement statement = getConnection().prepareStatement(sql);
        statement.executeUpdate();
    }

    public Connection getConnection() {
        return connection;
    }
}
