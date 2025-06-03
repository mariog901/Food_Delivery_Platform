package repository;

import modele.Produs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdusRepository extends AbstractRepository<Produs> {

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO Produs(nume, pret, descriere, restaurant_id) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Produs WHERE id = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Produs";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE Produs SET nume = ?, pret = ?, descriere = ?, restaurant_id = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Produs WHERE id = ?";
    }

    @Override
    protected void setCreateParameters(PreparedStatement statement, Produs produs) throws SQLException {
        statement.setString(1, produs.getNume());
        statement.setDouble(2, produs.getPret());
        statement.setString(3, produs.getDescriere());
        statement.setInt(4, produs.getRestaurantId());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Produs produs) throws SQLException {
        statement.setString(1, produs.getNume());
        statement.setDouble(2, produs.getPret());
        statement.setString(3, produs.getDescriere());
        statement.setInt(4, produs.getRestaurantId());
        statement.setInt(5, produs.getId());
    }

    @Override
    protected Produs mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Produs(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getDouble("pret"),
                rs.getString("descriere"),
                rs.getInt("restaurant_id")
        );
    }
    public List<Produs> findByRestaurantId(int restaurantId) {
        List<Produs> produse = new ArrayList<>();
        String query = "SELECT * FROM Produs WHERE restaurant_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, restaurantId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                produse.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Logheaza eroarea
        }
        return produse;
    }
}
