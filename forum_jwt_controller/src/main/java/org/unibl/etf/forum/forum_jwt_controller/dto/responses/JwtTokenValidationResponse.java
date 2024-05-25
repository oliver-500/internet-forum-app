package org.unibl.etf.forum.forum_jwt_controller.dto.responses;

import lombok.Data;

@Data
public class JwtTokenValidationResponse {
    private boolean isSuccessfull;
    private Integer userGroup;
    private String username;
}
