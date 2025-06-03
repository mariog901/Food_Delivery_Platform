package repository;

import modele.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ComandaRepository extends AbstractRepository<Comanda> {

    private final UtilizatorRepository utilizatorRepository;
    private final RestaurantRepository restaurantRepository;
    private final ProdusRepository produsRepository;
    private static final Logger LOGGER = Logger.getLogger(ComandaRepository.class.getName());
    private final RecenzieRepository recenzieRepository;
    public ComandaRepository() {
        super();
        this.utilizatorRepository = new UtilizatorRepository();
        this.restaurantRepository = new RestaurantRepository();
        this.produsRepository = new ProdusRepository();
        this.recenzieRepository = new RecenzieRepository();
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO Comanda (utilizator_id, restaurant_id, cost, status, data_plasare, data_livrare) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Comanda WHERE id = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Comanda";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE Comanda SET utilizator_id = ?, restaurant_id = ?, cost = ?, status = ?, data_plasare = ?, data_livrare = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Comanda WHERE id = ?";
    }

    @Override
    protected void setCreateParameters(PreparedStatement statement, Comanda comanda) throws SQLException {
        statement.setInt(1, comanda.getUtilizator().getId());
        statement.setInt(2, comanda.getRestaurant().getId());
        statement.setDouble(3, comanda.getCost());
        statement.setString(4, comanda.getStatus());
        statement.setTimestamp(5, Timestamp.valueOf(comanda.getDataPlasare()));

        if (comanda.getDataLivrare() != null) {
            statement.setTimestamp(6, Timestamp.valueOf(comanda.getDataLivrare()));
        } else {
            statement.setNull(6, Types.TIMESTAMP);
        }
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Comanda comanda) throws SQLException {
        statement.setInt(1, comanda.getUtilizator().getId());
        statement.setInt(2, comanda.getRestaurant().getId());
        statement.setDouble(3, comanda.getCost());
        statement.setString(4, comanda.getStatus());
        statement.setTimestamp(5, Timestamp.valueOf(comanda.getDataPlasare()));

        if (comanda.getDataLivrare() != null) {
            statement.setTimestamp(6, Timestamp.valueOf(comanda.getDataLivrare()));
        } else {
            statement.setNull(6, Types.TIMESTAMP);
        }

        statement.setInt(7, comanda.getId());
    }

    @Override
    protected Comanda mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int utilizatorId = rs.getInt("utilizator_id");
        int restaurantId = rs.getInt("restaurant_id");
        double cost = rs.getDouble("cost");
        String status = rs.getString("status");
        Timestamp tsPlasare = rs.getTimestamp("data_plasare");
        Timestamp tsLivrare = rs.getTimestamp("data_livrare");

        Optional<Utilizator> utilizatorOpt = utilizatorRepository.getById(utilizatorId);
        Utilizator utilizator = utilizatorOpt.orElse(null);

        Optional<Restaurant> restaurantOpt = restaurantRepository.getById(restaurantId);
        Restaurant restaurant = restaurantOpt.orElse(null);

        LocalDateTime dataPlasare = tsPlasare != null ? tsPlasare.toLocalDateTime() : null;
        LocalDateTime dataLivrare = tsLivrare != null ? tsLivrare.toLocalDateTime() : null;
        List<Produs> produse = getProdusePentruComanda(id);

        Comanda comanda = new Comanda(utilizator, restaurant, produse);
        comanda.setId(id);
        comanda.setCost(rs.getDouble("cost"));
        comanda.setStatus(rs.getString("status"));
        comanda.setDataPlasare(dataPlasare);
        comanda.setDataLivrare(dataLivrare);
        comanda.setProduse(produse);
        Optional<Recenzie>recenzieOpt = recenzieRepository.findByComandaId(id);
        recenzieOpt.ifPresent(comanda::setRecenzie);
        return comanda;

    }

    private List<Produs> getProdusePentruComanda(int comandaId) throws SQLException {
        List<Produs> produse = new ArrayList<>();
        String sql = "SELECT produs_id FROM Comanda_Produs WHERE comanda_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, comandaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int produsId = rs.getInt("produs_id");
                    Optional<Produs> produsOpt = produsRepository.getById(produsId);
                    produsOpt.ifPresent(produse::add);
                }
            }
        }
        return produse;
    }


    public void saveProdusePentruComanda(Comanda comanda) throws SQLException {
        String deleteSql = "DELETE FROM Comanda_Produs WHERE comanda_id = ?";
        String insertSql = "INSERT INTO Comanda_Produs (comanda_id, produs_id, cantitate) VALUES (?, ?, ?)";
        boolean originalAutoCommit = connection.getAutoCommit();
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql);
             PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {

            connection.setAutoCommit(false);

            // sterge produsele existente
            deleteStmt.setInt(1, comanda.getId());
            deleteStmt.executeUpdate();

            // insereaza produsele noi
            for (Produs produs : comanda.getProduse()) {
                insertStmt.setInt(1, comanda.getId());
                insertStmt.setInt(2, produs.getId());
                insertStmt.setInt(3, 1); //
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // anuleaza tranzactia in caz de eroare
                } catch (SQLException ex) {
                    LOGGER.log(
                            Level.SEVERE,
                            "Eroare la rollback tranzactie",
                            ex
                    );
                }
            }
            LOGGER.log(
                    Level.SEVERE,
                    "Eroare la salvarea produselor comenzii",
                    e
            );
            throw e; // Propaga exceptia
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(originalAutoCommit); // Restaureaza starea autoCommit
                } catch (SQLException ex) {
                    LOGGER.log(
                            Level.SEVERE,
                            "Eroare la restaurarea autoCommit",
                            ex
                    );
                }
            }

        }

    }
}