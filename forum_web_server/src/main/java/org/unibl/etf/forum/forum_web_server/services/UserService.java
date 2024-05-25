package org.unibl.etf.forum.forum_web_server.services;


import org.unibl.etf.forum.forum_web_server.dto.UserDTO;
import org.unibl.etf.forum.forum_web_server.dto.requests.UserActivationRequest;
import org.unibl.etf.forum.forum_web_server.dto.responses.PredicateResponse;
import org.unibl.etf.forum.forum_web_server.dto.responses.UserWithActivationInfo;

import java.util.List;

public interface UserService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    boolean saveUser(UserDTO user);

    UserDTO findUserByUsername(String username);
    List<UserWithActivationInfo> findAllWithActivationInfo();

    PredicateResponse activateUser(UserActivationRequest req);
}
