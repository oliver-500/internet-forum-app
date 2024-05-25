package org.unibl.etf.forum.forum_waf.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.unibl.etf.forum.forum_waf.DTO.CommentDTO;
import org.unibl.etf.forum.forum_waf.DTO.requests.ApproveCommentRequest;
import org.unibl.etf.forum.forum_waf.DTO.requests.DeleteCommentRequest;
import org.unibl.etf.forum.forum_waf.DTO.requests.UpdateCommentRequest;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final RestTemplate restTemplate;

    public CommentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Value("${webserver.server.address}")
    private String webserverServerAddress;

    @PostMapping("/{id}/create")
    public ResponseEntity<Object> addComment(@Valid @RequestBody CommentDTO request, @PathVariable int id){
        HttpEntity<Object> httpEntity = new HttpEntity<>(request);

        ResponseEntity<Object> response = restTemplate.exchange(webserverServerAddress + "/comments/" + id + "/create", HttpMethod.POST,
                httpEntity, Object.class);
        //ResponseEntity<Object> response = restTemplate.postForEntity(authenticationControllerServerAddress + "/auth/authenticate", request, Object.class, headers);


        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Object> approveComment(@Valid @RequestBody ApproveCommentRequest request, @PathVariable int id){
        HttpEntity<Object> httpEntity = new HttpEntity<>(request);

        ResponseEntity<Object> response = restTemplate.exchange(webserverServerAddress + "/comments/" + id +"/approve", HttpMethod.POST,
                httpEntity, Object.class);
        //ResponseEntity<Object> response = restTemplate.postForEntity(authenticationControllerServerAddress + "/auth/authenticate", request, Object.class, headers);


        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Object> deleteComment(@Valid @RequestBody DeleteCommentRequest request, @PathVariable int id){
        HttpEntity<Object> httpEntity = new HttpEntity<>(request);


        ResponseEntity<Object> response = restTemplate.exchange(webserverServerAddress + "/comments/" + id + "/delete", HttpMethod.POST, httpEntity, Object.class);


        //ResponseEntity<Object> response = restTemplate.postForEntity(authenticationControllerServerAddress + "/auth/authenticate", request, Object.class, headers);


        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/deleteUnapproved/{id}")
    public ResponseEntity<Object> deleteUnapprovedComment(@Valid @RequestBody DeleteCommentRequest request, @PathVariable int id){

        HttpEntity<Object> httpEntity = new HttpEntity<>(request);
        ResponseEntity<Object> response = restTemplate.exchange(webserverServerAddress + "/comments/" + id + "/delete", HttpMethod.POST, httpEntity, Object.class);


        //ResponseEntity<Object> response = restTemplate.postForEntity(authenticationControllerServerAddress + "/auth/authenticate", request, Object.class, headers);


        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Object> updateComment(@PathVariable int id, @Valid @RequestBody UpdateCommentRequest request){
        HttpEntity<Object> httpEntity = new HttpEntity<>(request);

        ResponseEntity<Object> response = restTemplate.exchange(webserverServerAddress + "/comments/" + id + "/update", HttpMethod.POST,
                httpEntity, Object.class);
        //ResponseEntity<Object> response = restTemplate.postForEntity(authenticationControllerServerAddress + "/auth/authenticate", request, Object.class, headers);


        return ResponseEntity.ok(response.getBody());
    }


}
