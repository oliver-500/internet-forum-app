package org.unibl.etf.forum.forum_waf.DTO;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String address;
    private String url;
    private String user;
    private Object data;
}
