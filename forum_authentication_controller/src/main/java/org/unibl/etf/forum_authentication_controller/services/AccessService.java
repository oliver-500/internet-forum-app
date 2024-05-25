package org.unibl.etf.forum_authentication_controller.services;

import org.springframework.stereotype.Service;
import org.unibl.etf.forum_authentication_controller.model.requests.UserPermissionCheckRequest;
import org.unibl.etf.forum_authentication_controller.model.responses.PredicateResponse;

@Service
public interface AccessService {
    PredicateResponse checkPermissionForUser(UserPermissionCheckRequest req);
}
