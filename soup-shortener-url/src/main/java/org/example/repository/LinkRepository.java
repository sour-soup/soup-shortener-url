package org.example.repository;

import org.example.entity.LinkEntity;
import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
    boolean existsByUrlAndUserId(String url, Long userId);
    LinkEntity findByUrlAndUserId(String url, Long userId);
    List<LinkEntity> getLinkEntitiesByUser(UserEntity user);

    List<Long> getIdsBetweenUpdatedAt(Instant from, Instant to);
}