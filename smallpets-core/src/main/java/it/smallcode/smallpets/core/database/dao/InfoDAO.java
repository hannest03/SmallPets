package it.smallcode.smallpets.core.database.dao;
/*

Class created by SmallCode
12.03.2022 14:11

*/

import it.smallcode.smallpets.core.database.Database;
import it.smallcode.smallpets.core.database.dto.InfoDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoDAO implements IDAO{
    private Database database;

    public InfoDTO getInfo(String iname) throws SQLException {
        final String sql = "SELECT * FROM infos\n" +
                "\tWHERE iname=?;";

        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, iname);

        ResultSet result = statement.executeQuery();
        if(result.next()){
            InfoDTO infoDTO = new InfoDTO();
            infoDTO.setIname(result.getString("iname"));
            infoDTO.setIvalue(result.getString("ivalue"));

            result.close();
            statement.close();
            return infoDTO;
        }

        result.close();
        statement.close();

        return null;
    }

    public void updateInfo(InfoDTO infoDTO) throws SQLException {
        final String sql = "UPDATE infos SET ivalue=? WHERE iname=?";

        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, infoDTO.getIvalue());
        statement.setString(2, infoDTO.getIname());

        statement.executeUpdate();
        statement.close();
    }

    public void insertInfo(InfoDTO infoDTO) throws SQLException {
        final String sql = "INSERT INTO infos (iname, ivalue) VALUES (?,?)";

        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, infoDTO.getIname());
        statement.setString(2, infoDTO.getIvalue());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }
}
