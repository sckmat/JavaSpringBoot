package com.yegorov.Lab2.service;

import com.yegorov.Lab2.model.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Primary
public class ModifySourceRequestService implements ModifyRequestService {

    @Override
    public void modify(Request request) {
        String oldSource = request.getSource();
        request.setSource("Service 1");

        log.info("Changed request.source: {} -> {}", oldSource, request.getSource());

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        new RestTemplate().exchange(
                "http://localhost:8086/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {}
        );

        log.info("Request sent to Service 2 with updated source");
    }
}
