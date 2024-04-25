package org.example.repository;

import org.example.jdbc.JdbcUtils;
import org.example.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkRepositoryTest {
    @Test
    void testAddUser() throws SQLException {
        JdbcUtils.createConnection();
        UserEntity userEntity = new UserEntity("aboba");
        Connection connection = JdbcUtils.getConnection();
        String sql = "INSERT INTO Users (login) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, userEntity.login());
        preparedStatement.execute();
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
             preparedStatement) {
            if (generatedKeys.next()) {
                System.out.println(generatedKeys.getLong(1));
            }
        }
        preparedStatement.close();
        JdbcUtils.closeConnection();
    }

    @Test
    void testCheckUser() throws SQLException {
        JdbcUtils.createConnection();

        UserEntity userEntity = new UserEntity("aaaaaa");
        String sql = "SELECT id FROM Users WHERE login = ?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userEntity.login());
        ResultSet resultSet = statement.executeQuery();
        System.out.println(resultSet.next());
        JdbcUtils.closeConnection();
    }
}
