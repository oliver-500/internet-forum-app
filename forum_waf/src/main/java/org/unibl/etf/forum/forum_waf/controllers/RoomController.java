package org.unibl.etf.forum.forum_waf.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Value("${webserver.server.address}")
    private String webserverServerAddress;

    private final RestTemplate restTemplate;

    public RoomController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Object>> findAll(){
        String url = webserverServerAddress + "/rooms/all";
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class);
        List<Object> itemList = Arrays.asList(responseEntity.getBody());
        return ResponseEntity.ok(itemList);
    }

    @GetMapping("/allWithCommentsCount")
    public ResponseEntity<List<Object>> findAllWithCommentsCount(){
        String url = webserverServerAddress + "/rooms/allWithCommentsCount";
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class);
        List<Object> itemList = Arrays.asList(responseEntity.getBody());
        return ResponseEntity.ok(itemList);
    }

    @GetMapping("/{id}/withAllowedComments")
    public ResponseEntity<Object> findOneWithAllowedComments(@PathVariable int id){

        String url = webserverServerAddress + "/rooms/" + id + "/withAllowedComments";
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Object resObject = responseEntity.getBody();
        return ResponseEntity.ok(resObject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable int id){

        String url = webserverServerAddress + "/rooms/" + id;
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        Object resObject = responseEntity.getBody();
        return ResponseEntity.ok(resObject);
    }
//
//    @GetMapping("{id}/newCommentsCount")
//    public ResponseEntity<Object> getNewCommentsCount(@PathVariable int id){
//        ResponseEntity<Object> response = restTemplate.getForEntity(webserverServerAddress + "/rooms/" + id + "/newCommentsCount",
//                 Object.class);
//        return ResponseEntity.ok(response.getBody());
//    }
}
