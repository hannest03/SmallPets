package it.smallcode.smallpets.core.database.dao;
/*

Class created by SmallCode
10.02.2022 20:58

*/

import it.smallcode.smallpets.core.database.Database;
import it.smallcode.smallpets.core.database.dto.SettingsDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SettingsDAO implements IDAO{
    private Database database;

    public SettingsDTO[] getSettings(String uid) throws SQLException {
        final String sql = "SELECT * FROM settings\n" +
                        "\tWHERE uid = ?;";
        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, uid);

        List<SettingsDTO> list = new LinkedList<>();

        ResultSet result = statement.executeQuery();
        while(result.next()){
            SettingsDTO settingsDTO = new SettingsDTO();
            settingsDTO.setUid(result.getString("uid"));
            settingsDTO.setSname(result.getString("sname"));
            settingsDTO.setSvalue(result.getString("svalue"));

            list.add(settingsDTO);
        }

        result.close();
        statement.close();

        return list.toArray(new SettingsDTO[0]);
    }

    public boolean hasSetting(String uid, String name) throws SQLException {
        final String sql = "SELECT COUNT(*) FROM settings\n" +
                            "\tWHERE uid=? AND sname=?;";

        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, uid);
        statement.setString(2, name);

        ResultSet result = statement.executeQuery();
        result.next();
        int count = result.getInt(1);

        result.close();
        statement.close();

        return count > 0;
    }

    public void insertSetting(SettingsDTO settingsDTO) throws SQLException {
        final String sql = "INSERT INTO settings (uid, sname, svalue) VALUES (?,?,?)";

        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, settingsDTO.getUid());
        statement.setString(2, settingsDTO.getSname());
        statement.setString(3, settingsDTO.getSvalue());

        statement.executeUpdate();
        statement.close();
    }

    public void updateSetting(SettingsDTO settingsDTO) throws SQLException {
        final String sql = "UPDATE settings SET svalue = ? WHERE uid = ? AND sname = ?";

        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, settingsDTO.getSvalue());
        statement.setString(2, settingsDTO.getUid());
        statement.setString(3, settingsDTO.getSname());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }
}
