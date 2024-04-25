package org.example.controller;

import org.example.controller.dto.LinkDto;
import org.example.controller.dto.UserDto;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.service.LinkService;
import org.example.service.model.Link;
import org.example.service.model.User;
import org.example.utils.BaseConversionException;
import org.example.utils.URLValidator;

import java.util.ArrayList;
import java.util.List;

public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    public LinkDto addLink(UserDto userDto, LinkDto linkDto) throws BaseConversionException, ParseShortLinkException {
        if (!URLValidator.isValid(linkDto.longLink())) throw new ParseShortLinkException("invalid address format");

        Link link = linkService.addLink(new User(userDto.login()), new Link(linkDto.longLink(), linkDto.shortLink()));
        return new LinkDto(link.longLink(), link.shortLink());
    }

    public LinkDto getLink(LinkDto linkDto) throws BaseConversionException, EntityNotFoundException, ParseShortLinkException {
        if (!URLValidator.isValid(linkDto.shortLink())) throw new ParseShortLinkException("invalid address format");

        Link link = linkService.getLink(new Link("", linkDto.shortLink()));
        return new LinkDto(link.longLink(), link.shortLink());
    }

    public List<LinkDto> getUserLinks(UserDto userDto) {
        List<Link> linkList = linkService.getUserLinks(new User(userDto.login()));
        List<LinkDto> list = new ArrayList<>();
        for (var p : linkList)
            list.add(new LinkDto(p.longLink(), p.shortLink()));
        return list;
    }
}
