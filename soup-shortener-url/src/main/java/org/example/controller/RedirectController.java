package org.example.controller;

import org.example.exception.EntityNotFoundException;
import org.example.service.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController {
    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping(value = "/{shortLink}")
    public RedirectView getLink(@PathVariable("shortLink") String shortLink) throws EntityNotFoundException {
        System.out.println("shortLink: " + shortLink);
        String longLink = linkService.getLink(shortLink).longLink();
        return new RedirectView(longLink);
    }
}
