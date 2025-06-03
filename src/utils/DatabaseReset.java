package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseReset {

    public static void resetAllTables() {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (Statement statement = connection.createStatement()) {
            // Dezactivează foreign key checks
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");

            // Șterge toate datele
            statement.execute("TRUNCATE TABLE recenzie");
            statement.execute("TRUNCATE TABLE comanda_produs");
            statement.execute("TRUNCATE TABLE comanda");
            statement.execute("TRUNCATE TABLE produs");
            statement.execute("TRUNCATE TABLE restaurant");
            statement.execute("TRUNCATE TABLE utilizator");
            statement.execute("TRUNCATE TABLE adresa");

            // Resetează auto_increment
            statement.execute("ALTER TABLE adresa AUTO_INCREMENT = 1");
            statement.execute("ALTER TABLE utilizator AUTO_INCREMENT = 1");
            statement.execute("ALTER TABLE restaurant AUTO_INCREMENT = 1");
            statement.execute("ALTER TABLE produs AUTO_INCREMENT = 1");
            statement.execute("ALTER TABLE comanda AUTO_INCREMENT = 1");
            statement.execute("ALTER TABLE recenzie AUTO_INCREMENT = 1");

            // Reactivează foreign key checks
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");


        } catch (SQLException e) {
            System.err.println("Eroare la resetarea tabelelor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}