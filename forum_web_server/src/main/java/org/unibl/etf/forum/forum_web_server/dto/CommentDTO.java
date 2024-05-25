package org.unibl.etf.forum.forum_web_server.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.unibl.etf.forum.forum_web_server.entities.RoomEntity;
import org.unibl.etf.forum.forum_web_server.entities.UserEntity;

import java.sql.Timestamp;

@Data
public class CommentDTO {


    private Integer id;
    private Boolean approved;

    private Timestamp postedDatetime;


    private String author;
    private int userId;
    private int roomId;


    private String content;


}
