package ac.id.unindra.spk.topsis.djingga.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    // Informasi koneksi ke database SQL
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/spk-djingga";
    private static final String username = "root";
    private static final String password = "";

    // Metode untuk membuat koneksi ke database
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(jdbcUrl, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Metode untuk menutup koneksi
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
