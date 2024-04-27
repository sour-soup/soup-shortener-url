package org.example.repository.Impl;

import org.example.repository.AuthorizeRepository;
import org.example.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AuthorizeRepositoryImpl implements AuthorizeRepository {
    private final Connection connection;

    public AuthorizeRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addUser(UserEntity userEntity) throws SQLException {
        String sql = "INSERT INTO Users (login) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, userEntity.login());
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public Boolean checkUser(UserEntity userEntity) throws SQLException {
        String sql = "SELECT id FROM Users WHERE login = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userEntity.login());
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
}
