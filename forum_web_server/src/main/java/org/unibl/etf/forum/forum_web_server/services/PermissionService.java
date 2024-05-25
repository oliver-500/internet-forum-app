package org.unibl.etf.forum.forum_web_server.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_web_server.dto.PermissionDTO;

import java.util.List;

public interface PermissionService {
    List<PermissionDTO> getAll();

    PermissionDTO getOneById(int id);

    PermissionDTO getOneByName(String name);
}
