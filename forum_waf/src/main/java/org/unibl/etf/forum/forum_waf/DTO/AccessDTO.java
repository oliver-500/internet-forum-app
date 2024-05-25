package org.unibl.etf.forum.forum_waf.DTO;

import lombok.Data;

@Data
public class AccessDTO {
    private RoomDTO room;
    private PermissionDTO permission;
}
