package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.exception.ParseShortLinkException;
import org.example.service.model.LongLink;
import org.example.service.model.ShortLink;
import org.example.service.model.User;
import org.example.utils.BaseConversionException;

public interface LinkService {
    ShortLink addLink(User user, LongLink longLink) throws BaseConversionException;

    LongLink getLink(ShortLink shortLink) throws BaseConversionException, EntityNotFoundException, ParseShortLinkException;
}
