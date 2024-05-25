package org.unibl.etf.forum_authentication_controller.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
