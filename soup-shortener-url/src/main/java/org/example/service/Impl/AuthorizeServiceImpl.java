package org.example.service.Impl;

import org.example.exception.AuthorizeException;
import org.example.repository.AuthorizeRepository;
import org.example.repository.entity.UserEntity;
import org.example.service.AuthorizeService;
import org.example.service.model.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {
    private final AuthorizeRepository authorizeRepository;

    public AuthorizeServiceImpl(AuthorizeRepository authorizeRepository) {
        this.authorizeRepository = authorizeRepository;
    }

    @Override
    public void login(User user) throws AuthorizeException {
        try {
            if (!authorizeRepository.checkUser(new UserEntity(user.login())))
                throw new AuthorizeException("the user does not exist");
        } catch (SQLException ex) {
            throw new AuthorizeException(ex.getMessage());
        }
    }

    @Override
    public void register(User user) throws AuthorizeException {
        try {
            if (authorizeRepository.checkUser(new UserEntity(user.login())))
                throw new AuthorizeException("the user already exists");
            authorizeRepository.addUser(new UserEntity(user.login()));
        } catch (SQLException ex) {
            throw new AuthorizeException(ex.getMessage());
        }
    }
}
