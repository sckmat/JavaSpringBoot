package com.yegorov.Lab2.service;

import com.yegorov.Lab2.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Qualifier("ModifyOperationUidResponseService")
public class ModifyOperationUidResponseService implements ModifyResponseService {

    @Override
    public Response modify(Response response) {
        String before = response.getOperationUid();
        String newUid = UUID.randomUUID().toString();
        response.setOperationUid(newUid);
        log.info("Changed response.operationUid: {} -> {}", before, newUid);
        return response;
    }
}
