package org.example.repository;

import org.example.repository.dao.IdDao;
import org.example.repository.dao.LinkDao;

public interface LinkRepository {
    void addLink(IdDao idDao, LinkDao linkDao);

    LinkDao getLink(IdDao idDao);

    IdDao getId(LinkDao linkDao);

    Boolean checkLink(LinkDao linkDao);

    Boolean checkId(IdDao idDao);

}
