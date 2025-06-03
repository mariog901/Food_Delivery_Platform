package repository;

import modele.Recenzie;
import modele.Utilizator;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecenzieRepository extends AbstractRepository<Recenzie> {

    private final UtilizatorRepository utilizatorRepository;

    public RecenzieRepository() {
        super();
        this.utilizatorRepository = new UtilizatorRepository();
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO Recenzie (comanda_id, nota, comentariu, data_recenzie, utilizator_id) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Recenzie WHERE id = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Recenzie";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE Recenzie SET nota = ?, comentariu = ?, data_recenzie = ?, utilizator_id = ?, comanda_id = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Recenzie WHERE id = ?";
    }

    @Override
    protected void setCreateParameters(PreparedStatement statement, Recenzie recenzie) throws SQLException {
        statement.setInt(1, recenzie.getComandaId());
        statement.setDouble(2, recenzie.getNota());
        statement.setString(3, recenzie.getComentariu());
        statement.setTimestamp(4, Timestamp.valueOf(recenzie.getData()));
        if (recenzie.getUtilizator() != null) {
            statement.setInt(5, recenzie.getUtilizator().getId());
        } else {
            statement.setNull(5, Types.INTEGER); // Daca utilizatorul poate fi null
        }
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Recenzie recenzie) throws SQLException {
        statement.setDouble(1, recenzie.getNota());
        statement.setString(2, recenzie.getComentariu());
        statement.setTimestamp(3, Timestamp.valueOf(recenzie.getData()));
        if (recenzie.getUtilizator() != null) {
            statement.setInt(4, recenzie.getUtilizator().getId());
        } else {
            statement.setNull(4, Types.INTEGER);
        }
        statement.setInt(5, recenzie.getComandaId());
        statement.setInt(6, recenzie.getId());
    }

    @Override
    protected Recenzie mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int comandaId = rs.getInt("comanda_id");
        double nota = rs.getDouble("nota");
        String comentariu = rs.getString("comentariu");
        Timestamp tsData = rs.getTimestamp("data_recenzie");
        LocalDateTime dataRecenzie = (tsData != null) ? tsData.toLocalDateTime() : null;
        int utilizatorId = rs.getInt("utilizator_id"); // presupunand ca exista

        Utilizator utilizator = null;
        if (utilizatorId > 0) { // verif daca utilizator_id este valid
            utilizator = utilizatorRepository.getById(utilizatorId).orElse(null);
        }

        return new Recenzie(id, nota, comentariu, utilizator, dataRecenzie, comandaId);
    }

    // Metoda specifica pentru a gasi recenzia unei comenzi
    public Optional<Recenzie> findByComandaId(int comandaId) {
        String query = "SELECT * FROM Recenzie WHERE comanda_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, comandaId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Logheaza eroarea
        }
        return Optional.empty();
    }
}