package org.example.service;

import org.example.entity.LinkEntity;
import org.example.entity.UserEntity;
import org.example.exception.EntityNotFoundException;
import org.example.repository.AuthorizeRepository;
import org.example.repository.LinkRepository;
import org.example.service.model.Link;
import org.example.service.model.User;
import org.example.utils.BaseConversion;
import org.example.utils.BaseConversionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class LinkServiceImpl implements LinkService {
    @Value("${base.url}")
    private String baseUrl;
    private final LinkRepository linkRepository;
    private final AuthorizeRepository authorizeRepository;
    private static final Long MIN_ID = 1L;
    private static final Long MAX_ID = (long) Math.pow(62, 5);

    public LinkServiceImpl(LinkRepository linkRepository, AuthorizeRepository authorizeRepository) {
        this.linkRepository = linkRepository;
        this.authorizeRepository = authorizeRepository;
    }

    @Override
    public Link addLink(User user, Link link) throws BaseConversionException {
        String longLink = link.longLink();
        UserEntity userEntity = authorizeRepository.getByLogin(user.login());

        String shortLink;
        if (linkRepository.existsByUrlAndUserId(longLink, userEntity.getId())) {
            Long id = linkRepository.findByUrlAndUserId(longLink, userEntity.getId()).getId();
            shortLink = BaseConversion.toBase(id);
        } else {
            Long id;
            Random random = new Random();
            do {
                id = (long) (MIN_ID + random.nextDouble() * (MAX_ID - MIN_ID));
            } while (linkRepository.existsById(id));
            shortLink = BaseConversion.toBase(id);
            LinkEntity linkEntity = new LinkEntity(id, longLink, userEntity);
            linkRepository.save(linkEntity);
        }
        return new Link(longLink, baseUrl + shortLink);
    }

    @Override
    public Link getLink(Link link) throws BaseConversionException, EntityNotFoundException {
        String shortLink = link.shortLink();
        Long id = BaseConversion.fromBase(shortLink);
        if (!linkRepository.existsById(id))
            throw new EntityNotFoundException("the link was not found");
        LinkEntity linkEntity = linkRepository.getReferenceById(id);
        return new Link(linkEntity.getUrl(), baseUrl + link.shortLink());
    }

    @Override
    public List<Link> getUserLinks(User user) {
        try {
            UserEntity userEntity = authorizeRepository.getByLogin(user.login());
            List<LinkEntity> userLinkEntities = linkRepository.getLinkEntitiesByUser(userEntity);
            List<Link> userLinks = new ArrayList<>();
            for (LinkEntity linkEntity : userLinkEntities) {
                String shortLink = baseUrl + BaseConversion.toBase(linkEntity.getId());
                Link link = new Link(shortLink, linkEntity.getUrl());
                userLinks.add(link);
            }
            return userLinks;
        } catch (BaseConversionException e) {
            throw new RuntimeException(e);
        }

    }
}
