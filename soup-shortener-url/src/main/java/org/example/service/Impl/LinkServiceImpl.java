package org.example.service.Impl;

import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.repository.LinkRepository;
import org.example.repository.entity.LinkEntity;
import org.example.repository.entity.UserEntity;
import org.example.service.LinkService;
import org.example.service.model.Link;
import org.example.service.model.User;
import org.example.utils.BaseConversion;
import org.example.utils.BaseConversionException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    public Link addLink(User user, Link link) throws BaseConversionException {
        try {
            LinkEntity linkEntity = new LinkEntity(link.longLink(), null);
            UserEntity userEntity = new UserEntity(user.login());

            if (linkRepository.checkLink(userEntity, linkEntity)) {
                Long id = linkRepository.getId(userEntity, linkEntity);
                String shortLink = BaseConversion.toBase(id);
                return new Link(link.longLink(), prefix + shortLink);
            }
            Long id;
            //Позже заменю на snowflake
            Random random = new Random();
            do {
                id = random.nextLong(MIN_ID, MAX_ID - MIN_ID);
            } while (linkRepository.checkId(id));
            String shortLink = BaseConversion.toBase(id);
            linkEntity = new LinkEntity(link.longLink(), id);
            linkRepository.addLink(userEntity, linkEntity);
            return new Link(link.longLink(), prefix + shortLink);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Link getLink(Link link) throws BaseConversionException, EntityNotFoundException, ParseShortLinkException {
        if (link.shortLink().indexOf(prefix) != 0) throw new ParseShortLinkException("invalid address format");
        String shortLink = link.shortLink().substring(prefix.length());
        Long id = BaseConversion.fromBase(shortLink);
        try {
            if (!linkRepository.checkId(id)) throw new EntityNotFoundException("the link was not found");
            LinkEntity linkEntity = linkRepository.getLink(id);
            return new Link(linkEntity.link(), link.shortLink());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Link> getUserLinks(User user) {
        try {
            List<LinkEntity> entityList = linkRepository.getUserLinks(new UserEntity(user.login()));
            List<Link> list = new ArrayList<>();
            for (var p : entityList) {
                String shortLink = prefix + BaseConversion.toBase(p.id());
                list.add(new Link(p.link(), shortLink));
            }
            return list;
        } catch (SQLException | BaseConversionException e) {
            throw new RuntimeException(e);
        }

    }
}
