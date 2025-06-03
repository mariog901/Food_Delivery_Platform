package repository;

import modele.Adresa;
import java.sql.*;
import java.util.Optional;

public class AdresaRepository extends AbstractRepository<Adresa> {
    @Override
    protected String getCreateQuery() {
        return "INSERT INTO Adresa(strada,numar,oras,cod_postal) VALUES(?,?,?,?)";
    }
    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Adresa WHERE id = ?";
    }
    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Adresa";
    }
    @Override
    protected String getUpdateQuery() {
        return "UPDATE Adresa SET strada = ?, numar = ?, oras = ? , cod_postal = ? WHERE id = ?";
    }
    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Adresa WHERE id = ?";
    }
    @Override
    protected void setCreateParameters(PreparedStatement statement, Adresa adresa) throws SQLException {
        statement.setString(1, adresa.getStrada());
        statement.setString(2, adresa.getNumar());
        statement.setString(3, adresa.getOras());
        statement.setString(4, adresa.getCodPostal());

    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Adresa adresa) throws SQLException {
        statement.setString(1, adresa.getStrada());
        statement.setString(2, adresa.getNumar());
        statement.setString(3, adresa.getOras());
        statement.setString(4, adresa.getCodPostal());
        statement.setInt(5,adresa.getId());
    }

    @Override
    protected Adresa mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String strada = rs.getString("strada");
        String numar = rs.getString("numar");
        String oras = rs.getString("oras");
        String codPostal = rs.getString("cod_postal");
        return new Adresa(id, strada, numar, oras, codPostal);
    }
}