package it.smallcode.smallpets.core.database.dao;
/*

Class created by SmallCode
07.02.2022 20:55

*/

import it.smallcode.smallpets.core.database.Database;
import it.smallcode.smallpets.core.database.dto.UserDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IDAO {
    private Database database;

    public UserDTO getUser(String uid) throws SQLException {
        final String sql = "SELECT * FROM users\n" +
                        "\tWHERE uid = ?;";
        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, uid);

        ResultSet result = statement.executeQuery();
        if(result.next()){
            UserDTO userDTO = new UserDTO();
            userDTO.setUid(result.getString("uid"));
            userDTO.setPselected(result.getString("pselected"));

            result.close();
            statement.close();
            return userDTO;
        }

        result.close();
        statement.close();
        return null;
    }

    public boolean existsUser(String uid){
        try {
            final UserDTO userDTO = getUser(uid);
            if(userDTO != null) return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void updateUser(UserDTO userDTO) throws SQLException {
        final String sql = "UPDATE users SET pselected=? WHERE uid=?";

        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, userDTO.getPselected());
        statement.setString(2, userDTO.getUid());

        statement.executeUpdate();
        statement.close();
    }

    public void insertUser(UserDTO userDTO) throws SQLException {
        final String sql = "INSERT INTO users (uid, pselected) VALUES (?,?)";

        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, userDTO.getUid());
        statement.setString(2, userDTO.getPselected());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }
}
