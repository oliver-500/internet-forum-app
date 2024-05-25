package org.unibl.etf.forum.forum_web_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.forum_web_server.dto.RoomDTO;
import org.unibl.etf.forum.forum_web_server.dto.RoomWithCommentsDTO;
import org.unibl.etf.forum.forum_web_server.dto.responses.RoomWithCommentsCountDTO;
import org.unibl.etf.forum.forum_web_server.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomDTO>> findAll(){
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/allWithCommentsCount")
    public ResponseEntity<List<RoomWithCommentsCountDTO>> findAllWithNewCommentsCount(){
        return ResponseEntity.ok(roomService.findAllWithCommentsCount());
    }


    @GetMapping("/{id}")
    public ResponseEntity<RoomWithCommentsDTO> getOne(@PathVariable Integer id){
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @GetMapping("/{id}/withAllowedComments")
    public ResponseEntity<RoomWithCommentsDTO> getOneWithAllowedComments(@PathVariable Integer id){
        return ResponseEntity.ok(roomService.getRoomWithAllowedComments(id));
    }


}
