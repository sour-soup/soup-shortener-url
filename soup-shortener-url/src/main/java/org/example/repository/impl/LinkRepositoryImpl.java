package org.example.repository.impl;

import org.example.database.EmulationDatabase;
import org.example.repository.LinkRepository;
import org.example.repository.dao.IdDao;
import org.example.repository.dao.LinkDao;

public class LinkRepositoryImpl implements LinkRepository {
    @Override
    public void addLink(IdDao idDao, LinkDao linkDao){
        EmulationDatabase.getInstance().addLink(idDao.id(), linkDao.link());
    }
    @Override
    public LinkDao getLink(IdDao idDao){
        String link = EmulationDatabase.getInstance().getLink(idDao.id());
        return new LinkDao(link);
    }
    @Override
    public IdDao getId(LinkDao linkDao){
        Long id = EmulationDatabase.getInstance().getId(linkDao.link());
        return new IdDao(id);
    }
    @Override
    public Boolean checkLink(LinkDao linkDao){
        return EmulationDatabase.getInstance().checkLink(linkDao.link());
    }

    @Override
    public Boolean checkId(IdDao idDao) {
        return EmulationDatabase.getInstance().checkId(idDao.id());
    }
}
