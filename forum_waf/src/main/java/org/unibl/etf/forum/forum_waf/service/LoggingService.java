package org.unibl.etf.forum.forum_waf.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.unibl.etf.forum.forum_waf.DTO.UserRequestDTO;


import java.util.concurrent.CompletableFuture;

@Service
public class LoggingService {

    @Value("${siem.address}")
    private String siemServerAddress;

    private final RestTemplate restTemplate;

    public LoggingService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }
    public void logMessage(UserRequestDTO req) {
        HttpEntity<UserRequestDTO> entity = new HttpEntity<UserRequestDTO>(req);

        restTemplate.postForEntity(siemServerAddress + "/userRequest", entity, Object.class );

    }
}
