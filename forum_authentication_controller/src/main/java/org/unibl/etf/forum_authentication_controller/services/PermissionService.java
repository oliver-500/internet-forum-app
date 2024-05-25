package org.unibl.etf.forum_authentication_controller.services;

import org.springframework.stereotype.Service;
import org.unibl.etf.forum_authentication_controller.model.dto.PermissionDTO;
import org.unibl.etf.forum_authentication_controller.model.requests.UserPermissionCheckRequest;
import org.unibl.etf.forum_authentication_controller.model.responses.PredicateResponse;
import org.unibl.etf.forum_authentication_controller.repositories.AccessEntityRepository;

@Service
public interface PermissionService {
    PermissionDTO getOneByName(String name);

}
