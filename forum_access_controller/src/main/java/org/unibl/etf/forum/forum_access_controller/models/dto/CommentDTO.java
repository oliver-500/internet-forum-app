package org.unibl.etf.forum.forum_access_controller.models.dto;


import lombok.Data;

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
