package org.unibl.etf.forum.forum_access_controller.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.unibl.etf.forum.forum_access_controller.models.dto.CommentDTO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
public class RestAPIController {


    private final RestTemplate restTemplate;


    private final HttpServletRequest servletRequest;
    @Value("${waf.controller.server.address}")
    private String wafControllerServerAddress;


    public RestAPIController(RestTemplate restTemplate, HttpServletRequest servletRequest) {
        this.restTemplate = restTemplate;
        this.servletRequest = servletRequest;
    }


    @PostMapping("/comments/{id}/create")
    public ResponseEntity<Object> addComment(@RequestBody CommentDTO request, @PathVariable int id){
        HttpEntity<Object> httpEntity = new HttpEntity<>(request);
        System.out.println(servletRequest.getAttribute("userId") + " ____________________________________ " + request.getUserId());
        if(!((Integer)servletRequest.getAttribute("userId") ==(request.getUserId()))) {
            System.out.println(servletRequest.getAttribute("userId") + " " + request.getUserId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }


        ResponseEntity<Object> response = restTemplate.exchange(wafControllerServerAddress + "/api/v1/comments/" + id + "/create", HttpMethod.POST,
                httpEntity, Object.class);
        //ResponseEntity<Object> response = restTemplate.postForEntity(authenticationControllerServerAddress + "/auth/authenticate", request, Object.class, headers);


        return ResponseEntity.ok(response.getBody());
    }


    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public ResponseEntity<?> forwardGetRequest(HttpServletRequest request) throws URISyntaxException {
        String targetUrl = determineTargetUrl(request.getRequestURI());
        if(targetUrl == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        System.out.println("idemo na " + targetUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(h -> h, request::getHeader)));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Object> response = restTemplate.exchange(new URI(targetUrl), HttpMethod.GET, entity, Object.class);
        return ResponseEntity.ok(response.getBody());
    }

    @RequestMapping(value = "/**", method = RequestMethod.POST)
    public ResponseEntity<?> forwardPostRequest(HttpServletRequest request, @RequestBody(required = false) Object body) throws URISyntaxException, IOException {
        String targetUrl = determineTargetUrl(request.getRequestURI());
        System.out.println(body);
        if(targetUrl == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        System.out.println("post idemo na " + targetUrl + " " + body);
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(h -> h, request::getHeader)));
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        System.out.println(headers.getContentType() + " -------");




        try {
            ResponseEntity<Object> response = restTemplate.exchange(new URI(targetUrl), HttpMethod.POST, entity, Object.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handle the 4xx and 5xx errors
                System.out.println(e.getMessage() + e.getStatusCode() + e.getStatusText());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");



        }


    }

    @RequestMapping(value = "/**", method = RequestMethod.DELETE)
    public ResponseEntity<?> forwardDeleteRequest(HttpServletRequest request, @RequestBody(required = false) Object body) throws URISyntaxException, IOException {
        String targetUrl = determineTargetUrl(request.getRequestURI());
        if(targetUrl == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        System.out.println("delete idemo na " + targetUrl + " " + body);
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(h -> h, request::getHeader)));
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Object> response = restTemplate.exchange(new URI(targetUrl), HttpMethod.DELETE, entity, Object.class);

        return ResponseEntity.ok(response.getBody());
    }


    private final static List<String> wafServerUrls = Arrays.asList("comments", "permissions", "rooms", "users", "accesses");

    public String determineTargetUrl(String uri){

        String returnUrl = null;

        for(String webServerUrl : wafServerUrls){
            if(uri.contains(webServerUrl)) returnUrl = wafControllerServerAddress + uri;
        }
        return returnUrl;
    }

}
