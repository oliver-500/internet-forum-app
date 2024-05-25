package org.unibl.etf.forum.forum_access_controller.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Boolean registered;
    private String email;
    private Integer userGroup;
}
