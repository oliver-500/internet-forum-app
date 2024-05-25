package org.unibl.etf.forum.forum_web_server.dto.responses;

import lombok.Data;

@Data
public class UserWithActivationInfo {
    private String username;
    private int id;
    private boolean registered;
    private int userGroup;
}
