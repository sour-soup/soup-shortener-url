package org.example.controller;

import org.example.controller.dto.LongLinkDto;
import org.example.controller.dto.ShortLinkDto;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.service.LinkService;
import org.example.service.model.LongLink;
import org.example.service.model.ShortLink;
import org.example.utils.BaseConversionException;
import org.example.utils.URLValidator;

public class LinkController {
    private static final String prefix = "soup-url.ru/";
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    public ShortLinkDto addLink(LongLinkDto longLinkDto)
            throws BaseConversionException, ParseShortLinkException {
        if (!URLValidator.isValid(longLinkDto.link()))
            throw new ParseShortLinkException("invalid address format");

        ShortLink shortLink = linkService.addLink(new LongLink(longLinkDto.link()));
        return new ShortLinkDto(prefix + shortLink.link());
    }

    public LongLinkDto getLink(ShortLinkDto shortLinkDto)
            throws BaseConversionException, EntityNotFoundException, ParseShortLinkException {
        if (shortLinkDto.link().indexOf(prefix) != 0)
            throw new ParseShortLinkException("invalid address format");
//        if (!URLValidator.isValid(shortLinkDto.link()))
//            throw new ParseShortLinkException("invalid address format");

        String link = shortLinkDto.link().substring(prefix.length());
        LongLink longLink = linkService.getLink(new ShortLink(link));
        return new LongLinkDto(longLink.link());
    }
}
