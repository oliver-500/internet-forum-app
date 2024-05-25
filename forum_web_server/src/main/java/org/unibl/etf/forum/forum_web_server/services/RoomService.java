package org.unibl.etf.forum.forum_web_server.services;

import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_web_server.dto.RoomDTO;
import org.unibl.etf.forum.forum_web_server.dto.RoomWithCommentsDTO;
import org.unibl.etf.forum.forum_web_server.dto.responses.RoomWithCommentsCountDTO;

import java.util.List;

@Service
public interface RoomService {
    List<RoomDTO> findAll();

    RoomWithCommentsDTO getRoom(int id);

//    NewCommentsCountDTO getNewCommentsCountByRoom(int roomId);

    RoomWithCommentsDTO getRoomWithAllowedComments(int id);

    List<RoomWithCommentsCountDTO> findAllWithCommentsCount();


}
