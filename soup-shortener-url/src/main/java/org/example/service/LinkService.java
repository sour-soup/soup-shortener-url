package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.service.model.Link;
import org.example.service.model.User;

import java.util.List;

public interface LinkService {
    Link addLink(User user, String longLink);

    Link getLink(String shortLink) throws EntityNotFoundException, ParseShortLinkException;

    List<Link> getUserLinks(User user);
}
