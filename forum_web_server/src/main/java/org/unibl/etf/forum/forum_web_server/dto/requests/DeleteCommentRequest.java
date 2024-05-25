package org.unibl.etf.forum.forum_web_server.dto.requests;

import lombok.Data;

@Data
public class DeleteCommentRequest {
    private int roomId;
    private int id;
}
