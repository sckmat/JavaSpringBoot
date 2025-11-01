package com.yegorov.Lab4.service;

import com.yegorov.Lab4.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}