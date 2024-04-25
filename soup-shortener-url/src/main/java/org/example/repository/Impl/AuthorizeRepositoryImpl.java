package org.example.repository.Impl;

import org.example.jdbc.JdbcUtils;
import org.example.repository.AuthorizeRepository;
import org.example.repository.entity.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizeRepositoryImpl implements AuthorizeRepository {
    public void addUser(UserEntity userEntity) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "INSERT INTO Users (login) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, userEntity.login());
        preparedStatement.execute();
        preparedStatement.close();
    }

    public Boolean checkUser(UserEntity userEntity) throws SQLException {
        String sql = "SELECT id FROM Users WHERE login = ?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userEntity.login());
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
}
