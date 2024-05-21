package org.example.controller;

import org.example.exception.AuthorizeException;
import org.example.service.AuthorizeService;
import org.example.service.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/authorize")
public class AuthorizeController {
    private final AuthorizeService authorizeService;

    public AuthorizeController(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    @GetMapping(value = "/login")
    public String login(@RequestParam String login) throws AuthorizeException {
        authorizeService.login(new User(login));
        return "Hello, " + login;
    }

    @GetMapping(value = "/register")
    public String register(@RequestParam String login) throws AuthorizeException {
        authorizeService.register(new User(login));
        return "Welcome, " + login;
    }

}
