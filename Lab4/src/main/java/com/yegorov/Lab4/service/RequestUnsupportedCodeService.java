package com.yegorov.Lab4.service;

import com.yegorov.Lab4.exception.UnsupportedCodeException;
import com.yegorov.Lab4.model.Request;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RequestUnsupportedCodeService implements UnsupportedCodeService {

    @Override
    public void validate(Request request) throws UnsupportedCodeException {
        if ("123".equals(request.getUid())) {
            log.error("Validation failed for uid={}", request.getUid());
            throw new UnsupportedCodeException("uid 123 not supported");
        }
    }
}
