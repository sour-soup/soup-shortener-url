package org.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtils {
    private static final String DB_URL = "jdbc:h2:~/db;AUTO_SERVER=TRUE";

    private static Connection connection;

    public static boolean createConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, "admin", "admin");
            return true;
        } catch (Exception ex) {
            System.out.println("Error occurred while connection to database: " + ex.getMessage());
        }
        return false;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error occurred while closing connection to database: " + ex.getMessage());
        }
    }
}
