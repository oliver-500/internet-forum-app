package org.unibl.etf.forum_authentication_controller.model.responses;

import lombok.Data;

@Data
public class    JwtTokenValidationResponse {
    private boolean isSuccessfull;
    private Integer userGroup;
    private String username;
    private int userId;
    private boolean activated;
}
