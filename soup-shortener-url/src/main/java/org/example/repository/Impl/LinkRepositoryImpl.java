package org.example.repository.Impl;

import org.example.jdbc.JdbcUtils;
import org.example.repository.LinkRepository;
import org.example.repository.entity.IdEntity;
import org.example.repository.entity.LinkEntity;
import org.example.repository.entity.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkRepositoryImpl implements LinkRepository {
    public void addLink(UserEntity userEntity, IdEntity idEntity, LinkEntity linkEntity) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "INSERT INTO Links (id, longLink, user_id) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, idEntity.id());
        preparedStatement.setString(2, linkEntity.link());
        Integer id = getUserId(userEntity.login());
        preparedStatement.setInt(3, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public LinkEntity getLink(IdEntity idEntity) throws SQLException {
        String sql = "SELECT longLink FROM Links WHERE id = ?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, idEntity.id());
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return null;
        return new LinkEntity(resultSet.getString("longLink"));
    }

    public IdEntity getId(UserEntity userEntity, LinkEntity linkEntity) throws SQLException {
        String sql = "SELECT id FROM Links WHERE user_id = ? AND longLink = ?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, getUserId(userEntity.login()));
        statement.setString(2, linkEntity.link());
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return null;
        return new IdEntity(resultSet.getLong("id"));
    }

    public Boolean checkLink(UserEntity userEntity, LinkEntity linkEntity) throws SQLException {
        String sql = "SELECT id FROM Links WHERE user_id = ? AND longLink = ?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, getUserId(userEntity.login()));
        statement.setString(2, linkEntity.link());
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public Boolean checkId(Long id) throws SQLException {
        String sql = "SELECT longLink FROM Links WHERE id = ?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    private Integer getUserId(String login) throws SQLException {
        String sql = "SELECT id FROM Users WHERE login = ?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return null;
        return resultSet.getInt("id");
    }
}