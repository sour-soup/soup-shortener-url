package org.example.repository;

import org.example.repository.entity.LinkEntity;
import org.example.repository.entity.UserEntity;

import java.sql.SQLException;
import java.util.List;

public interface LinkRepository {
    void addLink(UserEntity userEntity, LinkEntity linkEntity) throws SQLException;

    LinkEntity getLink(Long id) throws SQLException;

    Long getId(UserEntity userEntity, LinkEntity linkEntity) throws SQLException;

    Boolean checkLink(UserEntity userEntity, LinkEntity linkEntity) throws SQLException;

    Boolean checkId(Long id) throws SQLException;

    List<LinkEntity> getUserLinks(UserEntity userEntity) throws SQLException;
}
