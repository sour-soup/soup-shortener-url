package org.example.repository;

import org.example.repository.entity.LinkEntity;
import org.example.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
    boolean existsByUrlAndUserId(String url, UUID user_id);

    LinkEntity findByUrlAndUserId(String url, UUID user_id);

    List<LinkEntity> getLinkEntitiesByUser(UserEntity user);
}