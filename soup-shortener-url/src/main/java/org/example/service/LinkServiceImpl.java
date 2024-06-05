package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.kafka.VisitLinkSender;
import org.example.kafka.dto.VisitLinkMessage;
import org.example.repository.AuthorizeRepository;
import org.example.repository.LinkRepository;
import org.example.repository.entity.LinkEntity;
import org.example.repository.entity.UserEntity;
import org.example.service.model.Link;
import org.example.service.model.User;
import org.example.utils.BaseConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LinkServiceImpl implements LinkService {
    @Value("${base.url}")
    private String baseUrl;
    private static final Long MIN_ID = 1L;
    private static final Long MAX_ID = (long) Math.pow(64, 5);

    @Autowired
    private LinkServiceImpl self;
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private AuthorizeRepository authorizeRepository;
    @Autowired
    private VisitLinkSender visitLinkSender;


    @Override
    public Link addLink(User user, String longLink) {
        UserEntity userEntity = authorizeRepository.getByLogin(user.login());
        String shortLink;
        Long id;
        if (linkRepository.existsByUrlAndUserId(longLink, userEntity.getId())) {
            id = linkRepository.findByUrlAndUserId(longLink, userEntity.getId()).getId();
            shortLink = BaseConversion.toBase(id);
        } else {
            Random random = new Random();
            do {
                id = (long) (MIN_ID + random.nextDouble() * (MAX_ID - MIN_ID));
            } while (linkRepository.existsById(id));
            shortLink = BaseConversion.toBase(id);
            LinkEntity linkEntity = new LinkEntity(id, longLink, 0L, userEntity);
            linkRepository.save(linkEntity);
        }
        LinkEntity linkEntity = linkRepository.getReferenceById(id);
        return new Link(linkEntity.getUrl(), baseUrl + shortLink, linkEntity.getVisitCount());
    }

    @Cacheable(cacheNames = "link", cacheManager = "RedisCacheManager")
    public Link getLink(Long id) {
        LinkEntity linkEntity = linkRepository.getReferenceById(id);
        String shortLink = baseUrl + BaseConversion.toBase(linkEntity.getId());
        return new Link(linkEntity.getUrl(), shortLink, linkEntity.getVisitCount());
    }

    @Override
    public Link getLink(String shortLink) throws EntityNotFoundException {
        Long id = BaseConversion.fromBase(shortLink);
        System.out.println("link id: " + id);
        if (!linkRepository.existsById(id)) throw new EntityNotFoundException("the link was not found");
        visitLinkSender.sendMessage(new VisitLinkMessage(id));
        return self.getLink(id);
    }

    @Override
    public List<Link> getUserLinks(User user) {
        UserEntity userEntity = authorizeRepository.getByLogin(user.login());
        List<LinkEntity> userLinkEntities = linkRepository.getLinkEntitiesByUser(userEntity);
        return userLinkEntities.stream().map(linkEntity -> {
            String shortLink = baseUrl + BaseConversion.toBase(linkEntity.getId());
            return new Link(linkEntity.getUrl(), shortLink, linkEntity.getVisitCount());
        }).toList();
    }
}
