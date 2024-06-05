package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.exception.EntityNotFoundException;
import org.example.service.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Tag(
        name = "Контроллер переходов",
        description = "Управляет переходами по короткой ссылке"
)
@RestController
public class RedirectController {
    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Operation(
            summary = "Переход по ссылке",
            description = "Осуществляет перенаправление на длинную ссылку по короткой"
    )
    @GetMapping(value = "/{shortLink}")
    public RedirectView getLink(
            @PathVariable("shortLink") @Parameter(description = "Короткая ссылка") String shortLink) throws EntityNotFoundException {
        System.out.println("shortLink: " + shortLink);
        String longLink = linkService.getLink(shortLink).longLink();
        return new RedirectView(longLink);
    }
}
