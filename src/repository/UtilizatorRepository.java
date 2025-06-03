package repository;

import modele.Adresa;
import modele.Utilizator;

import java.sql.*;
import java.util.Optional;

public class UtilizatorRepository extends AbstractRepository<Utilizator> {

    private AdresaRepository adresaRepository = new AdresaRepository();

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO Utilizator(nume, email, telefon, adresa_id) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Utilizator WHERE id = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Utilizator";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE Utilizator SET nume = ?, email = ?, telefon = ?, adresa_id = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Utilizator WHERE id = ?";
    }

    @Override
    protected void setCreateParameters(PreparedStatement statement, Utilizator utilizator) throws SQLException {
        statement.setString(1, utilizator.getNume());
        statement.setString(2, utilizator.getEmail());
        statement.setString(3, utilizator.getTelefon());
        statement.setInt(4, utilizator.getAdresa().getId());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Utilizator utilizator) throws SQLException {
        statement.setString(1, utilizator.getNume());
        statement.setString(2, utilizator.getEmail());
        statement.setString(3, utilizator.getTelefon());
        statement.setInt(4, utilizator.getAdresa().getId());
        statement.setInt(5, utilizator.getId());
    }

    @Override
    protected Utilizator mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nume = rs.getString("nume");
        String email = rs.getString("email");
        String telefon = rs.getString("telefon");
        int adresaId = rs.getInt("adresa_id");


        Optional<Adresa> optionalAdresa = adresaRepository.getById(adresaId);
        Adresa adresa = optionalAdresa.orElse(null);

        return new Utilizator(id, nume, email, telefon, adresa);
    }
}
