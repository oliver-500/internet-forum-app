package org.unibl.etf.forum.forum_access_controller.models.responses;

import lombok.Data;

@Data
public class JwtTokenValidationResponse {
    private boolean isSuccessfull;
    private Integer userGroup;
    private int userId;
    private boolean activated;
}
