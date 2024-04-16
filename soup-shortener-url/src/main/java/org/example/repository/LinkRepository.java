package org.example.repository;

import org.example.repository.entity.IdEntity;
import org.example.repository.entity.LinkEntity;

public interface LinkRepository {
    void addLink(IdEntity idEntity, LinkEntity linkEntity);

    LinkEntity getLink(IdEntity idEntity);

    IdEntity getId(LinkEntity linkEntity);

    Boolean checkLink(LinkEntity linkEntity);

    Boolean checkId(Long id);

}
