package org.unibl.etf.forum_authentication_controller.services;

import org.springframework.stereotype.Service;
import org.unibl.etf.forum_authentication_controller.model.dto.UserDTO;


public interface UserService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    UserDTO saveUser(UserDTO user);

    UserDTO findUserByUsername(String username);
}
