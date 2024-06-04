package org.example.controller;

import org.example.controller.dto.LinkDto;
import org.example.service.LinkService;
import org.example.service.model.Link;
import org.example.service.model.User;
import org.example.utils.BaseConversionException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/links")
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping(value = "/addLink")
    public LinkDto addLink(@RequestParam String login, @RequestBody LinkDto linkDto) throws BaseConversionException {
        Link link = linkService.addLink(new User(login), new Link(linkDto.longLink(), linkDto.shortLink()));
        return new LinkDto(link.longLink(), link.shortLink());
    }

    @GetMapping(value = "/getLinks")
    public List<LinkDto> getUserLinks(@RequestParam String login) {
        List<Link> linkList = linkService.getUserLinks(new User(login));
        List<LinkDto> list = new ArrayList<>();
        for (var p : linkList)
            list.add(new LinkDto(p.longLink(), p.shortLink()));
        return list;
    }
}
