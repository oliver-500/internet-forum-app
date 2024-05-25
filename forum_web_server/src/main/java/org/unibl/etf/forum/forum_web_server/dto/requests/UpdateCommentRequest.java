package org.unibl.etf.forum.forum_web_server.dto.requests;

import lombok.Data;

@Data
public class UpdateCommentRequest {
    private String content;
    private int id;
}
