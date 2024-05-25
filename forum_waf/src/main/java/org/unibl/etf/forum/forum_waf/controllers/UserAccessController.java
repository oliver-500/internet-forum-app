package org.unibl.etf.forum.forum_waf.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.unibl.etf.forum.forum_waf.DTO.UserRoomAccessDTO;

@Controller
@RequestMapping("/api/v1/accesses")
public class UserAccessController {

    private final RestTemplate restTemplate;

    @Value("${webserver.server.address}")
    private String webserverServerAddress;



    public UserAccessController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("{id}")
    ResponseEntity<Object> getAllForUser(@PathVariable int id){
        String url = webserverServerAddress + "/accesses/" + id;
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/myaccesses")
    public ResponseEntity<Object> getMyAcceses(@Valid @RequestBody UserRoomAccessDTO req){
        String url = webserverServerAddress + "/accesses/myaccesses";
        ResponseEntity<Object> response = restTemplate.postForEntity(url, req, Object.class);
        return ResponseEntity.ok(response.getBody());

    }
}
