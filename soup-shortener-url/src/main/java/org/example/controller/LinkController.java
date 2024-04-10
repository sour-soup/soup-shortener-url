package org.example.controller;

import org.example.controller.dto.LongLinkDto;
import org.example.controller.dto.ShortLinkDto;
import org.example.exception.EntityNotFoundException;
import org.example.service.LinkService;
import org.example.service.model.LongLink;
import org.example.service.model.ShortLink;
import org.example.utils.BaseConversionException;

public class LinkController {
    private static final String prefix = "localhost:8080/";
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    public ShortLinkDto addLink(LongLinkDto longLinkDto) throws BaseConversionException {
        ShortLink shortLink = linkService.addLink(new LongLink(longLinkDto.link()));
        return new ShortLinkDto(prefix + shortLink.link());
    }

    public LongLinkDto getLink(ShortLinkDto shortLinkDto) throws BaseConversionException, EntityNotFoundException {
        LongLink longLink = linkService.getLink(new ShortLink(shortLinkDto.link()));
        return new LongLinkDto(longLink.link());
    }
}
