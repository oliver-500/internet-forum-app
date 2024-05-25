package org.unibl.etf.forum.forum_jwt_controller.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.forum_jwt_controller.dto.JwtTokenRequest;
import org.unibl.etf.forum.forum_jwt_controller.dto.TokenValidationRequest;
import org.unibl.etf.forum.forum_jwt_controller.dto.UserDTO;
import org.unibl.etf.forum.forum_jwt_controller.dto.responses.JwtTokenValidationResponse;
import org.unibl.etf.forum.forum_jwt_controller.services.JwtService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @PostMapping("/getToken")
    public ResponseEntity<String> getToken(@RequestBody UserDTO user){
        return ResponseEntity.ok(jwtService.generateToken(new HashMap<String,Object>(), user));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<JwtTokenValidationResponse> isTokenValid(@RequestBody TokenValidationRequest request){

        return ResponseEntity.ok(jwtService.checkTokenValidityAndExtractUserGroup(request.getToken()));
    }

    @PostMapping("/extractUsername")
    public ResponseEntity<String> extractUsernameFromToken(@RequestBody String token) throws ExpiredJwtException {

        String username = jwtService.extractUsername(token);
        if(username != null)
           return ResponseEntity.ok(username);

        else return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not found");
    }
    @GetMapping("/get")
    public ResponseEntity<String> get(){

        return ResponseEntity.ok(System.getProperty("javax.net.ssl.trustStore") + "dsddsadsadsadsa " + jwtService.authorizationSecret);
    }
}
