package com.yegorov.Lab2.service;

import com.yegorov.Lab2.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}