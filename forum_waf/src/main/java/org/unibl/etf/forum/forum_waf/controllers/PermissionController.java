package org.unibl.etf.forum.forum_waf.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final RestTemplate restTemplate;

    @Value("${webserver.server.address}")
    private String webserverServerAddress;

    public PermissionController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll(){
        String url = webserverServerAddress + "/permissions";
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        return ResponseEntity.ok(response.getBody());

    }



}
