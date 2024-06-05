package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.controller.dto.LinkRequest;
import org.example.controller.dto.LinkResponse;
import org.example.service.LinkService;
import org.example.service.model.Link;
import org.example.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            description = "Позволяет добавить ссылку у пользоветеля"
    )
    @PostMapping(value = "/addLink")
    public LinkResponse addLink(
            @RequestParam @Parameter(description = "Логин пользователя") String login,
            @RequestBody @Parameter(description = "Длинная ссылка") LinkRequest linkRequest) {
        Link link = linkService.addLink(new User(login), linkRequest.link());
        return new LinkResponse(link.longLink(), link.shortLink(), link.visitCount());
    }

    @Operation(
            summary = "Получение ссылок пользователя",
            description = "Позволяет получить все ссылки пользователя"
    )
    @GetMapping(value = "/getLinks")
    public List<LinkResponse> getUserLinks(
            @RequestParam @Parameter(description = "Логин пользователя") String login) {
        List<Link> linkList = linkService.getUserLinks(new User(login));
        return linkList.stream()
                .map(link -> new LinkResponse(link.longLink(), link.shortLink(), link.visitCount()))
                .collect(Collectors.toList());
    }
}
