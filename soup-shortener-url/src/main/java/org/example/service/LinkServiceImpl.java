package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.repository.LinkRepository;
import org.example.repository.entity.IdEntity;
import org.example.repository.entity.LinkEntity;
import org.example.service.model.LongLink;
import org.example.service.model.ShortLink;
import org.example.utils.BaseConversion;
import org.example.utils.BaseConversionException;

import java.util.Random;

public class LinkServiceImpl implements LinkService {
    private static final String prefix = "https://soup-url.ru/";
    private final LinkRepository linkRepository;
    private static final Long MIN_ID = 1L;
    private static final Long MAX_ID = 56_800_235_583L;

    public LinkServiceImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public ShortLink addLink(LongLink longLink) throws BaseConversionException {
        LinkEntity linkEntity = new LinkEntity(longLink.link());
        if (linkRepository.checkLink(linkEntity)) {
            IdEntity idEntity = linkRepository.getId(linkEntity);
            String link = BaseConversion.toBase(idEntity.id());
            return new ShortLink(link);
        }
        Long id;
        Random random = new Random();
        do {
            id = random.nextLong(MIN_ID, MAX_ID - MIN_ID);
        } while (linkRepository.checkId(id));
        String link = BaseConversion.toBase(id);
        linkRepository.addLink(new IdEntity(id), linkEntity);
        return new ShortLink(prefix + link);
    }

    @Override
    public LongLink getLink(ShortLink shortLink)
            throws BaseConversionException, EntityNotFoundException, ParseShortLinkException {
        if (shortLink.link().indexOf(prefix) != 0)
            throw new ParseShortLinkException("invalid address format");
        String link = shortLink.link().substring(prefix.length());
        Long id = BaseConversion.fromBase(link);
        if (!linkRepository.checkId(id))
            throw new EntityNotFoundException("the link was not found");
        LinkEntity linkEntity = linkRepository.getLink(new IdEntity(id));
        return new LongLink(linkEntity.link());
    }

}
