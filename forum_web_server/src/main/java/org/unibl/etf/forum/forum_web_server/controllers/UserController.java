package org.unibl.etf.forum.forum_web_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unibl.etf.forum.forum_web_server.dto.requests.UserActivationRequest;
import org.unibl.etf.forum.forum_web_server.dto.responses.PredicateResponse;
import org.unibl.etf.forum.forum_web_server.dto.responses.UserWithActivationInfo;
import org.unibl.etf.forum.forum_web_server.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    ResponseEntity<List<UserWithActivationInfo>> findAll(){
        return ResponseEntity.ok(userService.findAllWithActivationInfo());
    }

    @PostMapping("/activate")
    ResponseEntity<PredicateResponse> activateUser(@RequestBody UserActivationRequest req){
        return ResponseEntity.ok(userService.activateUser(req));
    }



}
