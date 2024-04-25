package org.example.controller;

import org.example.controller.dto.UserDto;
import org.example.exception.AuthorizeException;
import org.example.service.AuthorizeService;
import org.example.service.model.User;

public class AuthorizeController {
    private final AuthorizeService authorizeService;

    public AuthorizeController(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    public void login(UserDto userDto) throws AuthorizeException {
        authorizeService.login(new User(userDto.login()));
    }

    public void register(UserDto userDto) throws AuthorizeException {
        authorizeService.register(new User(userDto.login()));
    }

}
