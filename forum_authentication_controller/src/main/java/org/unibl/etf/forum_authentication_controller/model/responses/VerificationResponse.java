package org.unibl.etf.forum_authentication_controller.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationResponse {
    private String token;
    private boolean isVerified;
    private int id;
    private int userGroup;
    private String username;


}
