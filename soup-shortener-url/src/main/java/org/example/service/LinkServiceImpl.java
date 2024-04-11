package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.LinkRepository;
import org.example.repository.dao.IdDao;
import org.example.repository.dao.LinkDao;
import org.example.service.model.LongLink;
import org.example.service.model.ShortLink;
import org.example.utils.BaseConversion;
import org.example.utils.BaseConversionException;

import java.util.Random;

public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;
    private static final Long MIN_ID = 1L;
    private static final Long MAX_ID = 56_800_235_583L;

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
        Long id;
        Random random = new Random();
        do {
            id = random.nextLong(MIN_ID, MAX_ID - MIN_ID);
        } while (linkRepository.checkId(new IdDao(id)));
        String link = BaseConversion.toBase(id);
        linkRepository.addLink(new IdDao(id), linkDao);
        return new ShortLink(link);
    }

    @Override
    public LongLink getLink(ShortLink shortLink) throws BaseConversionException, EntityNotFoundException {
        Long id = BaseConversion.fromBase(shortLink.link());
        if (!linkRepository.checkId(new IdDao(id))) throw new EntityNotFoundException("the link was not found");
        LinkDao linkDao = linkRepository.getLink(new IdDao(id));
        return new LongLink(linkDao.link());
    }

}
