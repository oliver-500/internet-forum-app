package org.unibl.etf.forum.forum_web_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unibl.etf.forum.forum_web_server.dto.PermissionDTO;
import org.unibl.etf.forum.forum_web_server.services.PermissionService;

import java.util.List;

@Controller
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService service;

    public PermissionController(PermissionService service) {
        this.service = service;
    }

    @GetMapping("")
    ResponseEntity<List<PermissionDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }
}
