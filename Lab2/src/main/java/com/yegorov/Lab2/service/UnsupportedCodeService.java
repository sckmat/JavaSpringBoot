package com.yegorov.Lab2.service;

import com.yegorov.Lab2.exception.UnsupportedCodeException;
import com.yegorov.Lab2.model.Request;

public interface UnsupportedCodeService {
    void validate(Request request) throws UnsupportedCodeException;
}
