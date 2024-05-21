package org.example.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class JdbcUtils {
    private static final String DB_URL = "jdbc:h2:~/db;AUTO_SERVER=TRUE";

    @Bean(value = "connection")
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, "admin", "admin");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
