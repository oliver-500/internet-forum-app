package org.unibl.etf.forum.forum_web_server.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_web_server.dto.*;
import org.unibl.etf.forum.forum_web_server.dto.responses.RoomWithCommentsCountDTO;
import org.unibl.etf.forum.forum_web_server.entities.RoomEntity;
import org.unibl.etf.forum.forum_web_server.repositories.RoomEntityRepository;
import org.unibl.etf.forum.forum_web_server.services.RoomService;
import org.unibl.etf.forum.forum_web_server.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class RoomServiceImpl implements RoomService {

    private final ModelMapper modelMapper;

    private final RoomEntityRepository roomRepo;
    private final CommentServiceImpl commentService;
    private final UserService userService;

    public RoomServiceImpl(ModelMapper modelMapper, RoomEntityRepository roomRepo, CommentServiceImpl commentService, UserService userService) {
        this.modelMapper = modelMapper;
        this.roomRepo = roomRepo;
        this.commentService = commentService;

        this.userService = userService;
    }

    @Override
    public List<RoomDTO> findAll() {
        List<RoomDTO> rooms = roomRepo.findAll().stream().map(room -> {
            return modelMapper.map(room, RoomDTO.class);
        }).collect(Collectors.toList());
        return rooms;
    }

    @Override
    public RoomWithCommentsDTO getRoom(int id) {

        Optional<RoomEntity> room = roomRepo.findById(id);
        if(room.isEmpty()) return null;


        RoomWithCommentsDTO roomWithCommentsDTO = new RoomWithCommentsDTO();
        modelMapper.map(room.get(), roomWithCommentsDTO);
        if(roomWithCommentsDTO.getComments().size() > 0)System.out.println(roomWithCommentsDTO.getComments().get(0).getPostedDatetime());
        roomWithCommentsDTO.setComments(room.get().getComments().stream().map(c ->{

            CommentDTO comm = modelMapper.map(c, CommentDTO.class);
            comm.setAuthor(c.getUser().getUsername());



           return comm;
        }).collect(Collectors.toList()));
        return roomWithCommentsDTO;

    }

    @Override
    public RoomWithCommentsDTO getRoomWithAllowedComments(int id){
        RoomWithCommentsDTO room = getRoom(id);
        room.setComments(room.getComments().stream().filter(c -> c.getApproved()).collect(Collectors.toList()));
        return room;
    }

//    @Override
//    public NewCommentsCountDTO getNewCommentsCountByRoom(int roomId) {
//
//        Long count = commentRepo.getRoomNewCommentsCount(roomId).stream().count();
//        NewCommentsCountDTO newCommentsCount = new NewCommentsCountDTO();
//        newCommentsCount.setCount(count);
//        return newCommentsCount;
//    }

    @Override
    public List<RoomWithCommentsCountDTO> findAllWithCommentsCount() {
        return this.findAll().stream().map(r ->{
            RoomWithCommentsCountDTO room = new RoomWithCommentsCountDTO();
            modelMapper.map(r, room);
            room.setNewCommentsCount(commentService.countNewRoomComments(room.getId()));
            return room;
        }).collect(Collectors.toList());
    }




}
