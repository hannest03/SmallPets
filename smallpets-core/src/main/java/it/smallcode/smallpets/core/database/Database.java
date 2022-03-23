package it.smallcode.smallpets.core.database;
/*

Class created by SmallCode
07.02.2022 20:19

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.database.dao.*;
import it.smallcode.smallpets.core.database.dto.InfoDTO;
import it.smallcode.smallpets.core.database.versions.DV1;
import it.smallcode.smallpets.core.database.versions.DV2;
import lombok.Data;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
    private final int VERSION = 2;

    private Connection connection;

    private final HashMap<Class<IDAO>, IDAO> daos = new HashMap<>();

    private DatabaseVersion[] databaseVersions;

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

        databaseVersions = new DatabaseVersion[]{
                new DV1(),
                new DV2()
        };
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

        checkVersion();
    }

    public void connect(DatabaseConfig config) throws SQLException {
        if(config == null) return;

        try {
            if(connection != null && !connection.isClosed()) return;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        connection = DriverManager.getConnection("jdbc:mysql://" + config.host + ":" + config.port + "/" + config.databaseName ,config.username,config.password);

        checkVersion();
    }

    public void disconnect(){
        try {
            if(connection == null || connection.isClosed()) return;
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void checkVersion(){
        int currentVersion = getVersion();
        List<DatabaseVersion> databaseVersions = Arrays.stream(this.databaseVersions)
                .filter(version -> version.getVersion() > currentVersion )
                .sorted(Comparator.comparing(DatabaseVersion::getVersion)).collect(Collectors.toList());

        if(databaseVersions.size() == 0) return;

        Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Upgrading database...");

        // EXECUTE EACH VERSION UPGRADE
        for(DatabaseVersion databaseVersion : databaseVersions){
            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Upgrading to version " + databaseVersion.getVersion() + "...");
            try {
                databaseVersion.execute(this);
                setVersion(databaseVersion.getVersion());

                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Upgraded to version " + databaseVersion.getVersion() + "!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Failed to upgrade to version " + databaseVersion.getVersion() + "!");
                return;
            }
        }
        Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "Upgraded database!");

        if(getVersion() > VERSION){
            Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§WARN: §7Database version higher than plugins");
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

    public void setVersion(int version) {
        InfoDAO infoDAO = getDao(InfoDAO.class);

        InfoDTO infoDTO = new InfoDTO();
        infoDTO.setIname("version");
        infoDTO.setIvalue(Integer.toString(version));

        try {
            if (getVersion() == -1) {
                infoDAO.insertInfo(infoDTO);
            } else {
                infoDAO.updateInfo(infoDTO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    @Data
    public static class DatabaseConfig{
        public String host;
        public int port;

        public String username;
        public String password;

        public String databaseName;
    }
}
