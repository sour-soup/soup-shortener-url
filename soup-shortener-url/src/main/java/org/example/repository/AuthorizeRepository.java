package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizeRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByLogin(String login);

    UserEntity getByLogin(String login);
}
