package org.unibl.etf.forum.forum_siem.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.forum_siem.DTO.UserRequestDTO;
import org.unibl.etf.forum.forum_siem.services.LogService;

@RestController
@RequestMapping("/userRequest")
public class UserRequestController {

    private final LogService logService;

    public UserRequestController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    void saveRequestLog(@RequestBody UserRequestDTO req){
        logService.addLog(req);
    }
}
