package org.example.controller;

import org.example.controller.dto.LinkRequest;
import org.example.controller.dto.LinkResponse;
import org.example.service.LinkService;
import org.example.service.model.Link;
import org.example.service.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/links")
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping(value = "/addLink")
    public LinkResponse addLink(@RequestParam String login, @RequestBody LinkRequest linkRequest) {
        Link link = linkService.addLink(new User(login), linkRequest.link());
        return new LinkResponse(link.longLink(), link.shortLink(), link.visitCount());
    }

    @GetMapping(value = "/getLinks")
    public List<LinkResponse> getUserLinks(@RequestParam String login) {
        List<Link> linkList = linkService.getUserLinks(new User(login));
        return linkList.stream()
                .map(link -> new LinkResponse(link.longLink(), link.shortLink(), link.visitCount()))
                .collect(Collectors.toList());
    }
}
