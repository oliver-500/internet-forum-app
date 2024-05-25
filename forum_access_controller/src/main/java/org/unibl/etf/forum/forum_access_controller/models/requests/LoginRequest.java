package org.unibl.etf.forum.forum_access_controller.models.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
