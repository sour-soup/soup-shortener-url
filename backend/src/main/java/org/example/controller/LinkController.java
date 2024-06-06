package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.controller.dto.LinkRequest;
import org.example.controller.dto.LinkResponse;
import org.example.service.LinkService;
import org.example.service.model.Link;
import org.example.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(
        name = "Контроллер ссылок",
        description = "Управляет ссылками пользователей"
)
@RestController
@RequestMapping(value = "/api/v1/links")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @Operation(
            summary = "Добавление ссылки",
            description = "Позволяет добавить ссылку у пользоветеля",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping(value = "/addLink")
    public LinkResponse addLink(
            @AuthenticationPrincipal Jwt principal,
            @RequestBody @Parameter(description = "Длинная ссылка") LinkRequest linkRequest) {
        UUID userId = UUID.fromString(principal.getClaimAsString("sub"));
        String username = principal.getClaimAsString("preferred_username");
        Link link = linkService.addLink(new User(userId, username), linkRequest.link());
        return new LinkResponse(link.longLink(), link.shortLink(), link.visitCount());
    }

    @Operation(
            summary = "Получение ссылок пользователя",
            description = "Позволяет получить все ссылки пользователя",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping(value = "/getLinks")
    public List<LinkResponse> getUserLinks(
            @AuthenticationPrincipal Jwt principal) {
        UUID userId = UUID.fromString(principal.getClaimAsString("sub"));
        String username = principal.getClaimAsString("preferred_username");
        List<Link> linkList = linkService.getUserLinks(new User(userId, username));
        return linkList.stream()
                .map(link -> new LinkResponse(link.longLink(), link.shortLink(), link.visitCount()))
                .collect(Collectors.toList());
    }
}
