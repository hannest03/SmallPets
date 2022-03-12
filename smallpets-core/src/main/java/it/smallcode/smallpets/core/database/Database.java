package it.smallcode.smallpets.core.database;
/*

Class created by SmallCode
07.02.2022 20:19

*/

import it.smallcode.smallpets.core.database.dao.*;
import it.smallcode.smallpets.core.database.dto.InfoDTO;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Database {
    public static final int VERSION = 1;

    private Connection connection;

    private final HashMap<Class<IDAO>, IDAO> daos = new HashMap<>();

    public Database(){
        final Class<IDAO>[] daoList = new Class[]{
                UserDAO.class,
                SettingsDAO.class,
                PetDAO.class,
                InfoDAO.class
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

    public void connect(DatabaseConfig config) throws SQLException {
        if(config == null) return;

        try {
            if(connection != null && !connection.isClosed()) return;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        connection = DriverManager.getConnection("jdbc:mysql://" + config.host + ":" + config.port + "/" + config.databaseName ,config.username,config.password);
        if(getVersion() == -1) {
            createTables();
            setVersion(VERSION);
        }else{
            checkVersion();
        }
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
                        ");",
                "CREATE TABLE IF NOT EXISTS infos (\n" +
                        "\tiname VARCHAR(100) NOT NULL,\n" +
                        "\tivalue TEXT,\n" +
                        "\tPRIMARY KEY(iname)\n" +
                        ");"
        };

        for(String sql: sqls){
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        }
    }

    public int getVersion(){
        InfoDAO infoDAO = getDao(InfoDAO.class);
        try {
            InfoDTO infoDTO = infoDAO.getInfo("version");
            if(infoDTO == null || infoDTO.getIvalue() == null) return -1;
            return Integer.parseInt(infoDTO.getIvalue());
        } catch (SQLException ex) {
            return -1;
        }
    }

    public void setVersion(int version){
        InfoDAO infoDAO = getDao(InfoDAO.class);

        InfoDTO infoDTO = new InfoDTO();
        infoDTO.setIname("version");
        infoDTO.setIvalue(Integer.toString(version));

        try {
            if(getVersion() == -1) {
                infoDAO.insertInfo(infoDTO);
            }else{
                infoDAO.updateInfo(infoDTO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void checkVersion(){
        // UPDATE DATABASE
    }

    public <T extends IDAO> T getDao(Class<T> type){
        if(daos.containsKey(type))
            return (T) daos.get(type);
        return null;
    }

    public Connection getConnection() {
        return connection;
    }

    @Data
    public static class DatabaseConfig{
        public String host;
        public int port;

        public String username;
        public String password;

        public String databaseName;
    }
}
