package org.example.controller;

import org.example.controller.dto.LinkDto;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.service.LinkService;
import org.example.service.model.Link;
import org.example.service.model.User;
import org.example.utils.BaseConversionException;
import org.example.utils.URLValidator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@RestController
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping(value = "/api/v1/addLink")
    public LinkDto addLink(@RequestParam String login, @RequestBody LinkDto linkDto) throws BaseConversionException, ParseShortLinkException {
        if (!URLValidator.isValid(linkDto.longLink())) throw new ParseShortLinkException("invalid address format");

        Link link = linkService.addLink(new User(login), new Link(linkDto.longLink(), linkDto.shortLink()));
        return new LinkDto(link.longLink(), link.shortLink());
    }
    @GetMapping(value = "/{shortLink}")
    public RedirectView getLink(@PathVariable("shortLink") String shortLink) throws BaseConversionException, EntityNotFoundException, ParseShortLinkException {
        Link link = linkService.getLink(new Link("", shortLink));
        return new RedirectView(link.longLink());
    }

    @GetMapping(value = "/api/v1/getLinks")
    public List<LinkDto> getUserLinks(@RequestParam String login) {
        List<Link> linkList = linkService.getUserLinks(new User(login));
        List<LinkDto> list = new ArrayList<>();
        for (var p : linkList)
            list.add(new LinkDto(p.longLink(), p.shortLink()));
        return list;
    }
}
