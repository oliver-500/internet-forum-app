package org.unibl.etf.forum.forum_web_server.dto;

import lombok.Data;

@Data
public class AccessDTO {
    private RoomDTO room;
    private PermissionDTO permission;
}
