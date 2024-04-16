package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.model.LongLink;
import org.example.model.ShortLink;
import org.example.utils.BaseConversionException;

public interface LinkService {
    ShortLink addLink(LongLink longLink) throws BaseConversionException;

    LongLink getLink(ShortLink shortLink) throws BaseConversionException, EntityNotFoundException;
}
