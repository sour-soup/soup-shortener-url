package org.example.service.impl;

import org.example.repository.LinkRepository;
import org.example.repository.dao.IdDao;
import org.example.repository.dao.LinkDao;
import org.example.service.LinkService;
import org.example.service.model.LongLink;
import org.example.service.model.ShortLink;
import org.example.utils.BaseConversion;
import org.example.utils.BaseConversionException;

import java.util.Random;
import java.util.random.RandomGenerator;

public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;
    private static Long MIN_ID = 1L;
    private static Long MAX_ID = 56800235583L;

    public LinkServiceImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public ShortLink addLink(LongLink longLink) throws BaseConversionException {
        LinkDao linkDao = new LinkDao(longLink.link());
        if (linkRepository.checkLink(linkDao)) {
            IdDao idDao = linkRepository.getId(linkDao);
            String link = BaseConversion.toBase(idDao.id());
            return new ShortLink(link);
        }
        Long id = MIN_ID + (long) (Math.random() * (MAX_ID - MIN_ID));
        while (linkRepository.checkId(new IdDao(id)))
            id = MIN_ID + (long) (Math.random() * (MAX_ID - MIN_ID));
        String link = BaseConversion.toBase(id);
        linkRepository.addLink(new IdDao(id), linkDao);
        return new ShortLink(link);
    }

    @Override
    public LongLink getLink(ShortLink shortLink) throws BaseConversionException {
        Long id = BaseConversion.fromBase(shortLink.link());
        if (!linkRepository.checkId(new IdDao(id)))
            throw new RuntimeException(); //допишу потом
        LinkDao linkDao = linkRepository.getLink(new IdDao(id));
        return new LongLink(linkDao.link());
    }

}
