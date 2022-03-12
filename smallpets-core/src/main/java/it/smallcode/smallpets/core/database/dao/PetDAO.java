package it.smallcode.smallpets.core.database.dao;
/*

Class created by SmallCode
10.02.2022 21:23

*/

import it.smallcode.smallpets.core.database.Database;
import it.smallcode.smallpets.core.database.dto.PetDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PetDAO implements IDAO{
    private Database database;

    public PetDTO[] getPets(String uid) throws SQLException {
        final String sql = "SELECT * FROM pets\n" +
                            "\tWHERE uid=?;";
        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, uid);

        List<PetDTO> petDTOs = new LinkedList<>();

        ResultSet result = statement.executeQuery();
        while(result.next()){
            PetDTO petDTO = new PetDTO();
            petDTO.setPid(result.getString("pid"));
            petDTO.setPtype(result.getString("ptype"));
            petDTO.setPexp(result.getLong("pexp"));
            petDTO.setUid(result.getString("uid"));

            petDTOs.add(petDTO);
        }

        result.close();
        statement.close();

        return petDTOs.toArray(new PetDTO[0]);
    }

    public void insertPet(PetDTO petDTO) throws SQLException {
        final String sql = "INSERT INTO pets (pid, ptype, pexp, uid) VALUES (?,?,?,?);";
        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, petDTO.getPid());
        statement.setString(2, petDTO.getPtype());
        statement.setLong(3, petDTO.getPexp());
        statement.setString(4, petDTO.getUid());

        statement.executeUpdate();
        statement.close();
    }

    public void updatePet(PetDTO petDTO) throws SQLException {
        final String sql = "UPDATE pets SET ptype = ?, pexp = ? WHERE pid = ? AND uid = ?;";
        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, petDTO.getPtype());
        statement.setLong(2, petDTO.getPexp());
        statement.setString(3, petDTO.getPid());
        statement.setString(4, petDTO.getUid());

        statement.executeUpdate();
        statement.close();
    }

    public void deletePet(PetDTO petDTO) throws SQLException {
        final String sql = "DELETE FROM pets WHERE pid = ? AND uid = ?;";
        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, petDTO.getPid());
        statement.setString(2, petDTO.getUid());

        statement.executeUpdate();
        statement.close();
    }

    public boolean pidExists(String pid) throws SQLException {
        final String sql = "SELECT COUNT(*) FROM pets\n" +
                "\tWHERE pid=?;";

        PreparedStatement statement = database.getConnection().prepareStatement(sql);
        statement.setString(1, pid);

        ResultSet result = statement.executeQuery();
        result.next();
        int count = result.getInt(1);

        result.close();
        statement.close();

        return count > 0;
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }
}
