package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.repository.LinkRepositoryImpl;
import org.example.service.model.LongLink;
import org.example.service.model.ShortLink;
import org.example.utils.BaseConversionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkServiceTest {
    private final LinkService linkService = new LinkServiceImpl(new LinkRepositoryImpl());

    @Test
    void testAddLink() {
        //given:
        LongLink link = new LongLink("qwerty.com");
        //when:
        try {
            ShortLink shortLink = linkService.addLink(link);
            LongLink longLink = linkService.getLink(shortLink);
            //then:
            assertEquals(longLink.link(), link.link());
        }
        //then:
        catch (BaseConversionException | ParseShortLinkException | EntityNotFoundException ex) {
            assert (false);
        }
    }

}
