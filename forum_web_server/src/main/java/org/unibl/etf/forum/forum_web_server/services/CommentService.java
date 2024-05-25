package org.unibl.etf.forum.forum_web_server.services;

import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_web_server.dto.CommentDTO;
import org.unibl.etf.forum.forum_web_server.dto.requests.ApproveCommentRequest;
import org.unibl.etf.forum.forum_web_server.dto.requests.DeleteCommentRequest;
import org.unibl.etf.forum.forum_web_server.dto.requests.UpdateCommentRequest;
import org.unibl.etf.forum.forum_web_server.dto.responses.PredicateResponse;

import java.util.function.Predicate;

@Service
public interface CommentService {
    CommentDTO addComment(CommentDTO commentDTO, int roomId);

    PredicateResponse approveComment(ApproveCommentRequest request);


    int countNewRoomComments(int id);

       PredicateResponse deleteComment(DeleteCommentRequest req);

    PredicateResponse updateComment(int id, UpdateCommentRequest req);
}
