package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.service.model.Link;
import org.example.service.model.User;
import org.example.utils.BaseConversionException;

import java.util.List;

public interface LinkService {
    Link addLink(User user, Link link) throws BaseConversionException;

    Link getLink(Link link) throws BaseConversionException, EntityNotFoundException, ParseShortLinkException;

    List<Link> getUserLinks(User user);
}
