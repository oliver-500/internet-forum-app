package org.unibl.etf.forum_authentication_controller.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum_authentication_controller.model.requests.UserPermissionCheckRequest;
import org.unibl.etf.forum_authentication_controller.model.responses.PredicateResponse;
import org.unibl.etf.forum_authentication_controller.services.AccessService;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

  private final AccessService accessService;

    public AuthorizationController(AccessService accessService) {
        this.accessService = accessService;
    }


    @PostMapping("/checkPermissionForUser")
    public ResponseEntity<PredicateResponse> checkPermissionForUser(@RequestBody UserPermissionCheckRequest req){
        return ResponseEntity.ok(accessService.checkPermissionForUser(req));
    }
}
