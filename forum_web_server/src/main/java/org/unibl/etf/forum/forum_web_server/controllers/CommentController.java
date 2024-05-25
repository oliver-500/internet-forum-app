package org.unibl.etf.forum.forum_web_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.forum_web_server.dto.CommentDTO;
import org.unibl.etf.forum.forum_web_server.dto.requests.ApproveCommentRequest;
import org.unibl.etf.forum.forum_web_server.dto.requests.DeleteCommentRequest;
import org.unibl.etf.forum.forum_web_server.dto.requests.UpdateCommentRequest;
import org.unibl.etf.forum.forum_web_server.dto.responses.PredicateResponse;
import org.unibl.etf.forum.forum_web_server.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("{id}/create")
    private ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment, @PathVariable int id){
        return ResponseEntity.ok(commentService.addComment(comment, id));
    }

    @PostMapping("/{id}/approve")
    private ResponseEntity<PredicateResponse> approveComment(@RequestBody ApproveCommentRequest request){
        return ResponseEntity.ok(commentService.approveComment(request));
    }
    @PostMapping("{id}/delete")
    private ResponseEntity<PredicateResponse> deleteComment(@RequestBody DeleteCommentRequest request, @PathVariable int id){
        return ResponseEntity.ok(commentService.deleteComment(request));
    }
    @PostMapping("/{id}/update")
    private ResponseEntity<PredicateResponse> updateComment(@PathVariable int id, @RequestBody UpdateCommentRequest req){
        return ResponseEntity.ok(commentService.updateComment(id, req));
    }
}
