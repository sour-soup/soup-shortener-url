package org.example.repository;

import org.example.repository.entity.IdEntity;
import org.example.repository.entity.LinkEntity;
import org.example.repository.entity.UserEntity;

import java.sql.SQLException;

public interface LinkRepository {
    void addLink(UserEntity userEntity, IdEntity idEntity, LinkEntity linkEntity) throws SQLException;

    LinkEntity getLink(IdEntity idEntity) throws SQLException;

    IdEntity getId(UserEntity userEntity, LinkEntity linkEntity) throws SQLException;

    Boolean checkLink(UserEntity userEntity, LinkEntity linkEntity) throws SQLException;

    Boolean checkId(Long id) throws SQLException;

}
