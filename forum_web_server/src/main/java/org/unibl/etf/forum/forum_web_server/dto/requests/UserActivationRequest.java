package org.unibl.etf.forum.forum_web_server.dto.requests;

import lombok.Data;
import org.unibl.etf.forum.forum_web_server.dto.UserAccessDTO;

@Data
public class UserActivationRequest {
    private UserAccessDTO userAccess;
    private boolean activated;
    private int userType;
    private int userId;
}
