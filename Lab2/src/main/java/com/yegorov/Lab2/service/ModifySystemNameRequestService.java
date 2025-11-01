package com.yegorov.Lab2.service;

import com.yegorov.Lab2.model.Systems;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.yegorov.Lab2.model.Request;

@Service
public class ModifySystemNameRequestService implements ModifyRequestService {

    @Override
    public void modify(Request request) {
        request.setSystemName(Systems.SERVICE);

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        new RestTemplate().exchange(
                "http://localhost:8086/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {}
        );
    }
}
