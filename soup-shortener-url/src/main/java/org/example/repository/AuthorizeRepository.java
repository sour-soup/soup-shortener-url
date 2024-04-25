package org.example.repository;

import org.example.repository.entity.UserEntity;

import java.sql.SQLException;

public interface AuthorizeRepository {
    void addUser(UserEntity userEntity) throws SQLException;

    Boolean checkUser(UserEntity userEntity) throws SQLException;
}
