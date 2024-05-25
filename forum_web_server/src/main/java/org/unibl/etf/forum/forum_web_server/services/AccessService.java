package org.unibl.etf.forum.forum_web_server.services;

import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_web_server.dto.AccessDTO;
import org.unibl.etf.forum.forum_web_server.dto.UserAccessDTO;
import org.unibl.etf.forum.forum_web_server.dto.UserRoomAccessDTO;
import org.unibl.etf.forum.forum_web_server.entities.AccessEntity;

import java.util.List;


public interface AccessService {
    UserAccessDTO getAllForUser(int id);
    void deleteAllForUser(int id);

    void add(List<AccessEntity> accesses);

    List<AccessDTO> getMyAccesses(UserRoomAccessDTO req);
}
