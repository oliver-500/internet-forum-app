package org.unibl.etf.forum.forum_access_controller.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.unibl.etf.forum.forum_access_controller.models.responses.GithubTokenRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.unibl.etf.forum.forum_access_controller.models.responses.PredicateResponse;
import org.unibl.etf.forum.forum_access_controller.models.responses.RegistrationResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {


    @Value("${authentication.controller.server.address}")
    private String authenticationControllerServerAddress;

    @Value("${waf.controller.server.address}")
    private String wafControllerServerAddress;

    @Value("${access.controller.server.address}")
    private String accessControllerServerAddress;

    private final RestTemplate restTemplate;


    private final HttpServletRequest request;
    public AuthController(RestTemplate restTemplate, HttpServletRequest request) {
        this.restTemplate = restTemplate;
        this.request = request;
    }

    @GetMapping("/ssl")
    public ResponseEntity<String> test(){

        ResponseEntity<String> response = restTemplate.getForEntity(authenticationControllerServerAddress + "/api/v1/auth/ssl", String.class);


        if (response.getStatusCode().is2xxSuccessful()){
            return ResponseEntity.ok("Success");
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("fail");

    }



    @GetMapping("/loginGithub")
    public ResponseEntity<GithubTokenRequest> loginWithGithub(){

        ResponseEntity<PredicateResponse> responsee = restTemplate.getForEntity(authenticationControllerServerAddress + "/api/v1/auth/saveReferer" + "?referer=" + this.request.getHeader("referer"), PredicateResponse.class);
        if(!responsee.getBody().isSuccessfull()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


        ResponseEntity<String> response = restTemplate.getForEntity(authenticationControllerServerAddress + "/oauth2/authorization/github" ,String.class);


        GithubTokenRequest request = new GithubTokenRequest();

        if (response.getStatusCode().is3xxRedirection()){
            String locationHeader = response.getHeaders().getFirst("Location");



//            System.out.println("Redirect URL: " + locationHeader + " _________________________" +
//                    + this.request.getCookies().length + "_____" +  + "  " + this.request.getRemoteAddr() );


            Enumeration<String> headerNames = this.request.getHeaderNames();
            StringBuilder headers = new StringBuilder();

            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    String headerValue = this.request.getHeader(headerName);
                    headers.append(headerName).append(": ").append(headerValue).append("\n");
                }
            }

            System.out.println(headers.toString());  // Print headers to console (server logs)



            request.setRedirectURL(locationHeader);
            return ResponseEntity.ok(request);
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(request);

    }

    @GetMapping("/oauth/callback")
    public ResponseEntity<Object> oauthCallback(@RequestParam("code") String code, HttpServletResponse responsee){

        ResponseEntity<Object> response = restTemplate.getForEntity(authenticationControllerServerAddress + "/api/v1/auth/oauth/callback" + "?code=" + code, Object.class);
        //if(token != null) System.out.println("joooooooj " + token);
        //ResponseEntity<Object> response = restTemplate.postForEntity(authenticationControllerServerAddress + "/auth/authenticate", request, Object.class, headers);

        if(response.getStatusCode().is3xxRedirection()){
            System.out.println("opa1 " + code);

            HttpHeaders headers2 = new HttpHeaders();


            String locationHeader = response.getHeaders().getFirst("Location");
            System.out.println("Redirect URL: " + locationHeader + "   " + request.getHeader("Origin") + " " + request.getRemoteAddr());

            headers2.setLocation(URI.create(locationHeader));


            HttpHeaders headers = response.getHeaders();

            if(headers.get("Set-Cookie")!= null){
                headers.get("Set-Cookie").forEach(cookie -> {
                    String[] cookieParts = cookie.split(";");
                    for (String cookiePart : cookieParts) {
                        if (cookiePart.trim().startsWith("username=")) {
                            // Create a new cookie
                            Cookie newCookie = new Cookie("username", cookiePart.trim().substring(9));
                            newCookie.setHttpOnly(true);
                            newCookie.setSecure(true); // Set to true if using HTTPS
                            newCookie.setPath("/");
                            newCookie.setMaxAge(60 * 60); // 1 hour

                            // Add the new cookie to the response
                            responsee.addCookie(newCookie);
                        }
                    }
                });


            }



            // Return the response with 302 status and Location header
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }




        return ResponseEntity.ok(response.getBody());
    }


    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public ResponseEntity<?> forwardGetRequest(HttpServletRequest request) throws URISyntaxException {
        String targetUrl = determineTargetUrl(request.getRequestURI());
        if(targetUrl == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        System.out.println("idemo na" + targetUrl);
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
        if(targetUrl == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        System.out.println("idemo na" + targetUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(h -> h, request::getHeader)));
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Object> response = restTemplate.exchange(new URI(targetUrl), HttpMethod.POST, entity, Object.class);

        return ResponseEntity.ok(response.getBody());
    }


    private final static List<String> authenticationServerURLS = Arrays.asList("register", "login", "requestJwt", "confirmLogin", "refreshSession", "validateToken");

    private final static List<String> webServerURLS = Arrays.asList("dsadsa", "dsadas");

    public String determineTargetUrl(String uri){

        String returnUrl = null;
        for(String authURL : authenticationServerURLS){
            if(uri.endsWith(authURL)) returnUrl = authenticationControllerServerAddress + uri;
        }
        for(String webServerUrl : webServerURLS){
            if(uri.endsWith(webServerUrl)) returnUrl = wafControllerServerAddress + uri;
        }
        return returnUrl;
    }



//    @PostMapping("/register")
//    public ResponseEntity<RegistrationResponse> register(@RequestBody Object request) {
//        ResponseEntity<RegistrationResponse> response = restTemplate.postForEntity(authenticationControllerServerAddress + "/auth/register", request, RegistrationResponse.class);
//        return ResponseEntity.ok(response.getBody());
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<Object> login(@RequestBody Object request, HttpServletRequest httpRequest) {
//        HttpEntity<Object> httpEntity = new HttpEntity<>(request);
//        ResponseEntity<Object> response = restTemplate.exchange(authenticationControllerServerAddress + "/auth/login", HttpMethod.POST,
//                httpEntity, Object.class);
//        return ResponseEntity.ok(response.getBody());
//    }
//
//
//
//
//
//
//    @PostMapping("/requestJwt")
//    public ResponseEntity<Object> requestJwt(@RequestBody Object request) {
//
//        HttpEntity<Object> httpEntity = new HttpEntity<>(request);
//        ResponseEntity<Object> response = restTemplate.exchange(authenticationControllerServerAddress + "/auth/requestJwt", HttpMethod.POST,
//                httpEntity, Object.class);
//        return ResponseEntity.ok(response.getBody());
//
//    }
//
//
//
//    @PostMapping("/confirmLogin")
//    public ResponseEntity<Object> confirmLogin(@RequestBody Object request, HttpServletRequest httpRequest) {
//        HttpEntity<Object> httpEntity = new HttpEntity<>(request);
//        ResponseEntity<Object> response = restTemplate.exchange(authenticationControllerServerAddress + "/auth/authenticate", HttpMethod.POST,
//                httpEntity, Object.class);
//        return ResponseEntity.ok(response.getBody());
//    }
//
//    @PostMapping("/refreshSession")
//    public ResponseEntity<Object> refreshSession(@RequestBody Object token) {
//        HttpEntity<Object> httpEntity = new HttpEntity<>(token);
//        ResponseEntity<Object> response = restTemplate.exchange(authenticationControllerServerAddress + "/auth/refreshSession", HttpMethod.POST,
//                httpEntity, Object.class);
//        return ResponseEntity.ok(response.getBody());
//
//    }

}
