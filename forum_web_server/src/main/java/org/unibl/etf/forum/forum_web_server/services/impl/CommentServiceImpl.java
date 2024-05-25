package org.unibl.etf.forum.forum_web_server.services.impl;


import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_web_server.dto.CommentDTO;
import org.unibl.etf.forum.forum_web_server.dto.requests.ApproveCommentRequest;
import org.unibl.etf.forum.forum_web_server.dto.requests.DeleteCommentRequest;
import org.unibl.etf.forum.forum_web_server.dto.requests.UpdateCommentRequest;
import org.unibl.etf.forum.forum_web_server.dto.responses.PredicateResponse;
import org.unibl.etf.forum.forum_web_server.entities.CommentEntity;
import org.unibl.etf.forum.forum_web_server.repositories.CommentEntityRepository;
import org.unibl.etf.forum.forum_web_server.services.CommentService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentEntityRepository repo;

    public CommentServiceImpl(ModelMapper modelMapper, CommentEntityRepository repo) {
        this.modelMapper = modelMapper;
        this.repo = repo;
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO, int roomId) {

        commentDTO.setPostedDatetime(Timestamp.valueOf(LocalDateTime.now()));
        commentDTO.setApproved(false);
        commentDTO.setRoomId(roomId);
        CommentEntity comment = modelMapper.map(commentDTO, CommentEntity.class);
        System.out.println(commentDTO.getUserId() + "   ___________user" + comment.getContent() + "_? " + commentDTO.getContent());

        return  modelMapper.map(repo.save(comment), CommentDTO.class);

    }

    @Override
    public PredicateResponse approveComment(ApproveCommentRequest request) {
        PredicateResponse response = new PredicateResponse();

        if(!request.isApproved()){
            repo.deleteById(request.getId());
            response.setSuccessful(true);
            return response;
        }

        Optional<CommentEntity> comment = repo.findById(request.getId());
        if(comment.isEmpty()) return response;
        //ukoliko je admin uradio prilagodjavanje komentara
        if(request.getContent() != null) comment.get().setContent(request.getContent());
        comment.get().setApproved(true);
        if(repo.save(comment.get()) != null) {
            response.setSuccessful(true);
        }
        return response;


    }

    @Override
    public int countNewRoomComments(int id) {
        return repo.getNumberOfNewComments(id);
    }

    @Override
    public PredicateResponse deleteComment(DeleteCommentRequest req) {
        repo.deleteById(req.getId());
        return new PredicateResponse(true);
    }

    @Override
    public PredicateResponse updateComment(int id, UpdateCommentRequest req) {
        PredicateResponse res  = new PredicateResponse();
        CommentEntity commentEntity = repo.getReferenceById(req.getId());
        if(!commentEntity.getApproved()) return res;

        commentEntity.setContent(req.getContent());
        commentEntity.setApproved(false);
        repo.save(commentEntity);

        res.setSuccessful(true);
        return res;

    }


}
