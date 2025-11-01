package com.yegorov.Lab4.service;

import com.yegorov.Lab4.model.Response;
import com.yegorov.Lab4.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService implements ModifyResponseService {
    @Override
    public Response modify(Response response) {
        String before = response.getSystemTime();
        String now = DateTimeUtil.getCustomFormat().format(new Date());
        response.setSystemTime(now);
        log.info("Changed response.systemTime: {} -> {}", before, now);
        return response;
    }
}

