package org.example.service;

import org.example.exception.AuthorizeException;
import org.example.service.model.User;

public interface AuthorizeService {
    void login(User user) throws AuthorizeException;

    void register(User user) throws AuthorizeException;
}
