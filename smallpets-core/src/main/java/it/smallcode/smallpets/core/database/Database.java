package it.smallcode.smallpets.core.database;
/*

Class created by SmallCode
07.02.2022 20:19

*/

import it.smallcode.smallpets.core.database.dao.IDAO;
import it.smallcode.smallpets.core.database.dao.PetDAO;
import it.smallcode.smallpets.core.database.dao.SettingsDAO;
import it.smallcode.smallpets.core.database.dao.UserDAO;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Database {
    private Connection connection;

    private HashMap<Class<IDAO>, IDAO> daos = new HashMap<>();

    public Database(){
        final Class<IDAO>[] daoList = new Class[]{
                UserDAO.class,
                SettingsDAO.class,
                PetDAO.class
        };

        for(Class<IDAO> clazz : daoList){
            try {
                IDAO idao = clazz.newInstance();
                idao.setDatabase(this);

                this.daos.put(clazz, idao);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

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
                        ");"
        };

        for(String sql: sqls){
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        }
    }

    public <T extends IDAO> T getDao(Class<T> type){
        if(daos.containsKey(type))
            return (T) daos.get(type);
        return null;
    }

    public Connection getConnection() {
        return connection;
    }
}
