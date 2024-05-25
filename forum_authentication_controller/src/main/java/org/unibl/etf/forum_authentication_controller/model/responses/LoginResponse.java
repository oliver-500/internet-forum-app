package org.unibl.etf.forum_authentication_controller.model.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private boolean isLoggedIn;
    private boolean isRegistered;
}
