package Connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class manages the database connections.
 * @author Praveen Eswar k
 * @since 19 FEB 2024
 */
public class Connections {
    // JDBC URL, username, and password for connecting to the database
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String username = "SYSTEM";
    private static final String password = "Praveen";
    // Connection object for maintaining the database connection
    public static Connection con;

    /**
     * Establishes a connection to the database.
     */
    public static void invoke() {
        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Establish the database connection
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            // Print stack trace if connection fails
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the existing database connection.
     */
    public static Connection getCon() {
        return con;
    }

    /**
     * Closes the database connection.
     */
    public static void close_connection() {
        try {
            // Close the database connection
            con.close();
        } catch (Exception e) {
            // Print error message if closing connection fails
            System.out.println(e);
        }
    }
}
