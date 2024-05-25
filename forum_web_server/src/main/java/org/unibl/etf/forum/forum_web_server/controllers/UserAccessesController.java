package org.unibl.etf.forum.forum_web_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.forum_web_server.dto.AccessDTO;
import org.unibl.etf.forum.forum_web_server.dto.UserAccessDTO;
import org.unibl.etf.forum.forum_web_server.dto.UserRoomAccessDTO;
import org.unibl.etf.forum.forum_web_server.services.AccessService;

import java.util.List;

@Controller
@RequestMapping("/accesses")
public class UserAccessesController {

    private final AccessService accessService;

    public UserAccessesController(AccessService accessService) {
        this.accessService = accessService;
    }

    @GetMapping("{id}")
    ResponseEntity<UserAccessDTO> getAllowedAccessesOfUser(@PathVariable int id){
        return ResponseEntity.ok(accessService.getAllForUser(id));
    }

    @PostMapping("/myaccesses")
    ResponseEntity<List<AccessDTO>> getMyAccesses(@RequestBody UserRoomAccessDTO req){
        return ResponseEntity.ok(accessService.getMyAccesses(req));
    }
}
