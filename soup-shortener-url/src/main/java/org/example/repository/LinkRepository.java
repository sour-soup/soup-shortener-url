package org.example.repository;

import org.example.repository.dao.IdDao;
import org.example.repository.dao.LinkDao;

public interface LinkRepository {
    public void addLink(IdDao idDao, LinkDao linkDao);
    public LinkDao getLink(IdDao idDao);
    public IdDao getId(LinkDao linkDao);
    public Boolean checkLink(LinkDao linkDao);
    public Boolean checkId(IdDao idDao);

}
