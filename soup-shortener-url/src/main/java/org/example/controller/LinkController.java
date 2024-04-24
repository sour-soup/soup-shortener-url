package org.example.controller;

import org.example.controller.dto.LongLinkDto;
import org.example.controller.dto.ShortLinkDto;
import org.example.controller.dto.UserDto;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.service.LinkService;
import org.example.service.model.LongLink;
import org.example.service.model.ShortLink;
import org.example.service.model.User;
import org.example.utils.BaseConversionException;
import org.example.utils.URLValidator;

public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    public ShortLinkDto addLink(UserDto userDto, LongLinkDto longLinkDto) throws BaseConversionException, ParseShortLinkException {
        if (!URLValidator.isValid(longLinkDto.link())) throw new ParseShortLinkException("invalid address format");

        ShortLink shortLink = linkService.addLink(new User(userDto.login()), new LongLink(longLinkDto.link()));
        return new ShortLinkDto(shortLink.link());
    }

    public LongLinkDto getLink(ShortLinkDto shortLinkDto) throws BaseConversionException, EntityNotFoundException, ParseShortLinkException {
        if (!URLValidator.isValid(shortLinkDto.link())) throw new ParseShortLinkException("invalid address format");

        LongLink longLink = linkService.getLink(new ShortLink(shortLinkDto.link()));
        return new LongLinkDto(longLink.link());
    }
}
