package org.example.repository;

import org.example.database.EmulationDatabase;
import org.example.repository.entity.IdEntity;
import org.example.repository.entity.LinkEntity;

public class LinkRepositoryImpl implements LinkRepository {
    @Override
    public void addLink(IdEntity idEntity, LinkEntity linkEntity) {
        EmulationDatabase.getInstance().addLink(idEntity.id(), linkEntity.link());
    }

    @Override
    public LinkEntity getLink(IdEntity idEntity) {
        String link = EmulationDatabase.getInstance().getLink(idEntity.id());
        return new LinkEntity(link);
    }

    @Override
    public IdEntity getId(LinkEntity linkEntity) {
        Long id = EmulationDatabase.getInstance().getId(linkEntity.link());
        return new IdEntity(id);
    }

    @Override
    public Boolean checkLink(LinkEntity linkEntity) {
        return EmulationDatabase.getInstance().checkLink(linkEntity.link());
    }

    @Override
    public Boolean checkId(Long id) {
        return EmulationDatabase.getInstance().checkId(id);
    }
}
