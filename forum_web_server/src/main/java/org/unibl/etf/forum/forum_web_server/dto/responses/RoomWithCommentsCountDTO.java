package org.unibl.etf.forum.forum_web_server.dto.responses;

import lombok.Data;

@Data
public class RoomWithCommentsCountDTO {
    private String name;
    private int id;
    private int newCommentsCount;
}
