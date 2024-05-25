package org.unibl.etf.forum.forum_web_server.dto.requests;

import lombok.Data;

@Data
public class ApproveCommentRequest {
    private String content;
    private Integer id;
    private boolean approved;
}
