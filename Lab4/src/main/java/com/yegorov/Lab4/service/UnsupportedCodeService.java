package com.yegorov.Lab4.service;

import com.yegorov.Lab4.exception.UnsupportedCodeException;
import com.yegorov.Lab4.model.Request;

public interface UnsupportedCodeService {
    void validate(Request request) throws UnsupportedCodeException;
}
