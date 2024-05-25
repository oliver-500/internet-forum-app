package org.unibl.etf.forum.forum_web_server.dto;

import lombok.Data;

@Data
public class UserRoomAccessDTO {
    private int userId;
    private int roomId;
}
