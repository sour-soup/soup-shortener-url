package org.example.service.Impl;

import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.repository.LinkRepository;
import org.example.repository.entity.IdEntity;
import org.example.repository.entity.LinkEntity;
import org.example.repository.entity.UserEntity;
import org.example.service.LinkService;
import org.example.service.model.LongLink;
import org.example.service.model.ShortLink;
import org.example.service.model.User;
import org.example.utils.BaseConversion;
import org.example.utils.BaseConversionException;

import java.sql.SQLException;
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
    public ShortLink addLink(User user, LongLink longLink) throws BaseConversionException {
        try {
            LinkEntity linkEntity = new LinkEntity(longLink.link());
            UserEntity userEntity = new UserEntity(user.login());

            if (linkRepository.checkLink(userEntity, linkEntity)) {
                IdEntity idEntity = linkRepository.getId(userEntity, linkEntity);
                String link = BaseConversion.toBase(idEntity.id());
                return new ShortLink(prefix + link);
            }
            Long id;
            //Позже заменю на snowflake
            Random random = new Random();
            do {
                id = random.nextLong(MIN_ID, MAX_ID - MIN_ID);
            } while (linkRepository.checkId(id));
            String link = BaseConversion.toBase(id);
            IdEntity idEntity = new IdEntity(id);
            linkRepository.addLink(userEntity, idEntity, linkEntity);
            return new ShortLink(prefix + link);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public LongLink getLink(ShortLink shortLink) throws BaseConversionException, EntityNotFoundException, ParseShortLinkException {
        if (shortLink.link().indexOf(prefix) != 0) throw new ParseShortLinkException("invalid address format");
        String link = shortLink.link().substring(prefix.length());
        Long id = BaseConversion.fromBase(link);
        try {
            if (!linkRepository.checkId(id)) throw new EntityNotFoundException("the link was not found");
            LinkEntity linkEntity = linkRepository.getLink(new IdEntity(id));
            return new LongLink(linkEntity.link());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
