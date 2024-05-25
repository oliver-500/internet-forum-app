package org.unibl.etf.forum.forum_web_server.dto;


import lombok.Data;

import java.util.List;

@Data
public class RoomWithCommentsDTO {
    private String name;
    private int id;
    private List<CommentDTO> comments;

}