package org.example.service;

import org.example.service.model.LongLink;
import org.example.service.model.ShortLink;
import org.example.utils.BaseConversionException;

public interface LinkService {
    public ShortLink addLink(LongLink longLink) throws BaseConversionException;
    public LongLink getLink(ShortLink shortLink) throws BaseConversionException;
}
