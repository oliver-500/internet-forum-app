package org.unibl.etf.forum_authentication_controller.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRequestDTO {
    private String address;
    private String url;
    private String user;
    private Object data;
}
