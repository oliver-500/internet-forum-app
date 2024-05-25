package org.unibl.etf.forum_authentication_controller.model.requests;

import lombok.Data;

@Data
public class RefreshSessionRequest {
    private String token;
}
