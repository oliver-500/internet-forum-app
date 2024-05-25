package org.unibl.etf.forum.forum_waf.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.unibl.etf.forum.forum_waf.DTO.requests.UserActivationRequest;

@RequestMapping("/api/v1/users")
@Controller
public class UserController {

    private final RestTemplate restTemplate;

    @Value("${webserver.server.address}")
    private String webserverServerAddress;

    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    ResponseEntity<Object> findAll(){
        String url = webserverServerAddress + "/users";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Object resObject = responseEntity.getBody();
        return ResponseEntity.ok(resObject);

    }

    @PostMapping("/activate")
    ResponseEntity<Object> activateUser(@Valid @RequestBody UserActivationRequest req){

        String url = webserverServerAddress + "/users/activate";
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, req, Object.class);
        Object resObject = responseEntity.getBody();
        return ResponseEntity.ok(resObject);
    }

}
