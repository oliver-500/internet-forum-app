package org.unibl.etf.forum.forum_siem.DTO;

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
