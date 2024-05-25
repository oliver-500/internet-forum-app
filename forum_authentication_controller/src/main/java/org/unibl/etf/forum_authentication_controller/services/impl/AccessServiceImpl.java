package org.unibl.etf.forum_authentication_controller.services.impl;

import org.springframework.stereotype.Service;
import org.unibl.etf.forum_authentication_controller.model.dto.PermissionDTO;
import org.unibl.etf.forum_authentication_controller.model.entities.AccessEntity;
import org.unibl.etf.forum_authentication_controller.model.entities.PermissionEntity;
import org.unibl.etf.forum_authentication_controller.model.requests.UserPermissionCheckRequest;
import org.unibl.etf.forum_authentication_controller.model.responses.PredicateResponse;
import org.unibl.etf.forum_authentication_controller.repositories.AccessEntityRepository;
import org.unibl.etf.forum_authentication_controller.services.AccessService;
import org.unibl.etf.forum_authentication_controller.services.PermissionService;

@Service
public class AccessServiceImpl implements AccessService {


        private final PermissionService permissionService;
        private final AccessEntityRepository accessEntityRepository;



    public AccessServiceImpl(PermissionService permissionService, AccessEntityRepository accessEntityRepository) {
        this.permissionService = permissionService;
        this.accessEntityRepository = accessEntityRepository;
    }

    @Override
        public PredicateResponse checkPermissionForUser(UserPermissionCheckRequest req) {
        PredicateResponse pe = new PredicateResponse();
        PermissionDTO permissionDTO = permissionService.getOneByName(req.getMethodName().toLowerCase());
        AccessEntity accessEntity = accessEntityRepository.getByUserAndPermissionAndRoom(req.getUserId(), permissionDTO.getId(), req.getRoomId());
        if(accessEntity == null){
            System.out.println(req + "   ********");
            System.out.println("nije pronadjen");
           return pe;
        }
        System.out.println("pronadjen");
        pe.setSuccessfull(true);
        return pe;
        }

}
