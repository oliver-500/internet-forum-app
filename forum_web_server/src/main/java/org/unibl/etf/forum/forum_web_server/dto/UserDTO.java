package org.unibl.etf.forum.forum_web_server.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private Boolean registered;
    private String email;
    private Integer userGroup;
    private String activationCode;
}
