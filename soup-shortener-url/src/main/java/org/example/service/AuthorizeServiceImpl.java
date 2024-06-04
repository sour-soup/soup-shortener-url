package org.example.service;

import org.example.entity.UserEntity;
import org.example.exception.AuthorizeException;
import org.example.repository.AuthorizeRepository;
import org.example.service.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {
    private final AuthorizeRepository authorizeRepository;

    public AuthorizeServiceImpl(AuthorizeRepository authorizeRepository) {
        this.authorizeRepository = authorizeRepository;
    }

    @Override
    public void login(User user) throws AuthorizeException {
        if (!authorizeRepository.existsByLogin(user.login()))
            throw new AuthorizeException("Login Failed: user does not exist");
    }

    @Override
    public void register(User user) throws AuthorizeException {
        if (authorizeRepository.existsByLogin(user.login()))
            throw new AuthorizeException("Login Failed: user already exists");
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(user.login());
        authorizeRepository.save(userEntity);
    }
}
