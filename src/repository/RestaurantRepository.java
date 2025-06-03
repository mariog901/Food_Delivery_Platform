package repository;

import modele.Restaurant;
import modele.Adresa;
import modele.Produs;
import java.sql.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class RestaurantRepository extends AbstractRepository<Restaurant> {

    private final AdresaRepository adresaRepository;
    private final ProdusRepository produsRepository;
    public RestaurantRepository() {
        super();
        this.adresaRepository = new AdresaRepository();
        this.produsRepository = new ProdusRepository();
    }

    @Override
    protected String getCreateQuery() {
        // rating-ul îl poți lăsa 0 la început, dacă vrei, sau îl setezi aici
        return "INSERT INTO Restaurant (nume, adresa_id, tip_bucatarie, rating) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM Restaurant WHERE id = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Restaurant";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE Restaurant SET nume = ?, adresa_id = ?, tip_bucatarie = ?, rating = ? WHERE id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM Restaurant WHERE id = ?";
    }

    @Override
    protected void setCreateParameters(PreparedStatement statement, Restaurant restaurant) throws SQLException {
        statement.setString(1, restaurant.getNume());
        statement.setInt(2, restaurant.getAdresa().getId());
        statement.setString(3, restaurant.getTipBucatarie());
        statement.setDouble(4, restaurant.getRating());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement statement, Restaurant restaurant) throws SQLException {
        statement.setString(1, restaurant.getNume());
        statement.setInt(2, restaurant.getAdresa().getId());
        statement.setString(3, restaurant.getTipBucatarie());
        statement.setDouble(4, restaurant.getRating());
        statement.setInt(5, restaurant.getId());
    }

    @Override
    protected Restaurant mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nume = rs.getString("nume");
        int adresaId = rs.getInt("adresa_id");
        String tipBucatarie = rs.getString("tip_bucatarie");
        double rating = rs.getDouble("rating");

        Optional<Adresa> adresaOptional = adresaRepository.getById(adresaId);
        Adresa adresa = adresaOptional.orElse(null);
        Restaurant restaurant = new Restaurant(id, nume, adresa, tipBucatarie, rating);
        List<Produs> produseMeniu = produsRepository.findByRestaurantId(id);
        if (restaurant.getMeniu() != null) { // Meniul este initializat in constructorul Restaurant
            produseMeniu.forEach(produs -> restaurant.getMeniu().adaugaProduse(produs));
        }
        return restaurant;
    }
}
