import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.exception.FlywayValidateException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Application {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/flywaydb";
    private static final String DB_USER = "rachid";
    private static final String DB_PASS = "password";
    private static final Flyway flyway = Flyway.configure().dataSource(DB_URL, DB_USER, DB_PASS).load();
    private static final Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            flyway.migrate();
        } catch (FlywayValidateException e) {
            System.err.println("Error running migrations: " + e);
            System.exit(1);
        }

        // first version of my application and the database
        /*
            try {
                ResultSet resultSet = conn.createStatement().executeQuery("SELECT id, name FROM product");
                if (resultSet.next()) {
                    final String id = resultSet.getString(1);
                    final String name = resultSet.getString(2);
                    System.out.println("the ID is " + id + ", and the name is " + name);
                }
            } catch (SQLException e) {
                System.err.println("Error inserting row: " + e);
            }
        */

        /* Second version of my application and the database
           The database changes in the code below are made in the V2__add_description_to_product.sql.
         */
        try {
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT id, name, description FROM product");
            while (resultSet.next()) {
                final String id = resultSet.getString(1);
                final String name = resultSet.getString(2);
                final String description = resultSet.getString(3);
                System.out.println("the ID is " + id + ", and the name is " + name +
                        ", and the description is " + description);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting row: " + e);
        }
    }
}