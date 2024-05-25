package org.unibl.etf.forum.forum_web_server.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserAccessDTO {
    private int userId;
    private List<AccessDTO> accesses;
}
